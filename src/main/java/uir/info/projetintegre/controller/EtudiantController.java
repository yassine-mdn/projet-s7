package uir.info.projetintegre.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.exception.ProgrammeNotFoundException;
import uir.info.projetintegre.model.*;
import uir.info.projetintegre.model.enums.Roles;
import uir.info.projetintegre.repository.*;
import uir.info.projetintegre.service.BatchCreateService;
import uir.info.projetintegre.service.EmailService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/etudiants")
public class EtudiantController {

    private final EtudiantRepository etudiantRepository;
    private final ProfesseurRepository professeurRepository;
    private final ProgrammeRepository programmeRepository;
    private final BatchCreateService batchCreateService;
    private final ResponssableDeStageRepository responssableDeStageRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;


    public EtudiantController(EtudiantRepository etudiantRepository,
                              ProfesseurRepository professeurRepository,
                              ProgrammeRepository programmeRepository,
                              BatchCreateService batchCreateService,
                              ResponssableDeStageRepository responssableDeStageRepository,
                              PasswordEncoder encoder,
                              EmailService emailService) {
        this.etudiantRepository = etudiantRepository;
        this.professeurRepository = professeurRepository;
        this.programmeRepository = programmeRepository;
        this.batchCreateService = batchCreateService;
        this.responssableDeStageRepository = responssableDeStageRepository;

        this.encoder = encoder;
        this.emailService = emailService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void addEtudiant(@RequestBody NewEtudiantRequest request) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(request.nom());
        etudiant.setPrenom(request.prenom());
        etudiant.setEmail(request.email());
        etudiant.setPassWord(encoder.encode(request.passWord()));
        etudiant.setNiveauEtude(request.niveauEtude());
        etudiant.setRole(Roles.STUDENT);
        etudiant.setProgramme(programmeRepository.findById(request.programmeId).orElseThrow(() -> new ProgrammeNotFoundException(request.programmeId)));
        etudiantRepository.save(etudiant);
        emailService.sendAccountInfo(request.prenom, request.email, request.passWord);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("batch")
    public ResponseEntity<?> batchAddEtudiant(@RequestParam("file") MultipartFile fichier) throws IOException {
        String response = batchCreateService.createEtudiantFromFile(fichier);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //TODO: USe Pagination to get only 25 items at a time (saving resources) (todo 7itach fiha tamara bezzaf)
    //chi nhar insha'allah
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @GetMapping("{etudiant_id}")
    public Etudiant getEtudiantById(@PathVariable("etudiant_id") Integer id) {
        return etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("{etudiant_id}/encadrant")
    public Professeur getProfesseurByIdEtudiant(@PathVariable("etudiant_id") Integer id) {
        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return etudiant.getProfesseur();
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("{etudiant_id}/superviseur")
    public ResponssableDeStage getRDSByIdEtudiant(@PathVariable("etudiant_id") Integer id) {
        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return etudiant.getSuperviseur();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("{etudiant_id}/reunion")
    public List<Reunion> getReunionByIdEtudiant(@PathVariable("etudiant_id") Integer id){
        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        List<Reunion> sortedReunion = new ArrayList<>(etudiant.getReunions());
        sortedReunion.sort(Comparator.comparing(Reunion::getDate).reversed());
        return sortedReunion;
    }


    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @PutMapping("{etudiant_id}")
    public void updateEtudiant(@PathVariable("etudiant_id") Integer id, @RequestBody NewEtudiantRequest request) {
        etudiantRepository.findById(id)
                .map(etudiant -> {
                            etudiant.setNom(request.nom);
                            etudiant.setPrenom(request.prenom);
                            etudiant.setEmail(request.email);
                            etudiant.setPassWord(request.passWord);
                            etudiant.setNiveauEtude(request.niveauEtude);
                            etudiant.setProgramme(programmeRepository.findById(request.programmeId).orElseThrow(() -> new ProgrammeNotFoundException(request.programmeId)));
                            etudiant.setSuperviseur(responssableDeStageRepository.findById(request.superviseurId).orElseThrow(() -> new CompteNotFoundException(request.superviseurId)));
                            etudiant.setProfesseur(professeurRepository.findById(request.encadrantId).orElseThrow(() -> new CompteNotFoundException(request.encadrantId)));
                            return etudiantRepository.save(etudiant);
                        }
                );
        //TODO:Add case of recieving an invalid id
    }

    //nssa ghir kount 7mar
    @PreAuthorize("hasAnyRole('ADMIN','PROF')")
    @PutMapping("{etudiant_id}/encadrant/{encadrant_id}")
    public void setEtudiantEncadrant(@PathVariable("etudiant_id") Integer idEtudiant, @PathVariable("encadrant_id") Integer idEncadrant) {

        etudiantRepository.findById(idEtudiant)
                .map(etudiant -> {
                            etudiant.setProfesseur(professeurRepository.findById(idEncadrant)
                                    .orElseThrow(() -> new CompteNotFoundException(idEtudiant)));
                            return etudiantRepository.save(etudiant);
                        }
                );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{etudiant_id}/superviseur/{superviseur_id}")
    public void setEtudiantSuperviseur(@PathVariable("etudiant_id") Integer id, @PathVariable("superviseur_id") Integer idSuperviseur) {

        etudiantRepository.findById(id)
                .map(etudiant -> {
                            etudiant.setSuperviseur(responssableDeStageRepository.findById(idSuperviseur)
                                    .orElseThrow(() -> new CompteNotFoundException(id)));
                            return etudiantRepository.save(etudiant);
                        }
                );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{etudiant_id}")
    public void deleteEtudiant(@PathVariable("etudiant_id") Integer id) {
        etudiantRepository.deleteById(id);
    }

    record NewEtudiantRequest(
            String nom,
            String prenom,
            String email,
            String passWord,
            Integer niveauEtude,
            Integer programmeId,
            Integer encadrantId,
            Integer superviseurId
    ) {
    }
}
