package uir.info.projetintegre.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.exception.ProgrammeNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.Professeur;
import uir.info.projetintegre.model.enums.Roles;
import uir.info.projetintegre.repository.ProfesseurRepository;
import uir.info.projetintegre.repository.ProgrammeRepository;
import uir.info.projetintegre.service.EmailService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/professeurs")
public class ProfesseurController {

    private final ProfesseurRepository professeurRepository;
    private final ProgrammeRepository programmeRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;


    public ProfesseurController(ProfesseurRepository professeurRepository, ProgrammeRepository programmeRepository, PasswordEncoder encoder, EmailService emailService) {
        this.professeurRepository = professeurRepository;
        this.programmeRepository = programmeRepository;
        this.encoder = encoder;
        this.emailService = emailService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void addProfesseur(@RequestBody NewProfesseurRequest request) {
        Professeur professeur = new Professeur();
        professeur.setNom(request.nom());
        professeur.setPrenom(request.prenom());
        professeur.setEmail(request.email());
        professeur.setPassWord(encoder.encode(request.passWord()));
        professeur.setRole(Roles.PROF);
        professeur.setProgramme(programmeRepository.findById(request.programmeId).orElseThrow(() -> new ProgrammeNotFoundException(request.programmeId)));
        professeur.setEtudiants(request.etudiants());
        professeurRepository.save(professeur);
        emailService.sendAccountInfo(request.prenom, request.email, request.passWord);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROF')")
    @GetMapping("{prof_id}")
    public Professeur getProfesseurById(@PathVariable("prof_id") Integer id){
        return professeurRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @PreAuthorize("hasRole('PROF')")
    @GetMapping("{prof_id}/etudiant")
    public List<Etudiant> getEtudiantByIdProfesseur(@PathVariable("prof_id") Integer id){
        Professeur professeur = professeurRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return new ArrayList<>(professeur.getEtudiants());
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROF')")
    @PutMapping("{prof_id}")
    public void updateProfesseur(@PathVariable("prof_id") Integer id, @RequestBody NewProfesseurRequest request) {
        professeurRepository.findById(id)
                .map(professeur -> {
                            professeur.setNom(request.nom());
                            professeur.setPrenom(request.prenom());
                            professeur.setEmail(request.email());
                            professeur.setPassWord(request.passWord());
                            professeur.setProgramme(programmeRepository.findById(request.programmeId).orElseThrow(() -> new ProgrammeNotFoundException(request.programmeId)));
                            professeur.setEtudiants(request.etudiants());
                            return professeurRepository.save(professeur);
                        }
                );
        //TODO:Add case of recieving an invalid id
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROF')")
    @DeleteMapping("{prof_id}")
    public void deleteProfesseur(@PathVariable("prof_id") Integer id){
        professeurRepository.deleteById(id);
    }

    record NewProfesseurRequest(
            String nom,
            String prenom,
            String email,
            String passWord,
            Integer programmeId,
            Set<Etudiant> etudiants

    ){}
}
