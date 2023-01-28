package uir.info.projetintegre.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.JoinTableCompte;
import uir.info.projetintegre.model.ResponssableDeStage;
import uir.info.projetintegre.model.Reunion;
import uir.info.projetintegre.repository.JoinTableCompteRepository;
import uir.info.projetintegre.repository.ResponssableDeStageRepository;
import uir.info.projetintegre.service.EmailService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/responsables")
public class RespossableDeStageController {

    private final ResponssableDeStageRepository responssableDeStageRepository;
    private final JoinTableCompteRepository joinTableCompteRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    public RespossableDeStageController(ResponssableDeStageRepository responssableDeStageRepository, JoinTableCompteRepository joinTableCompteRepository, PasswordEncoder encoder, EmailService emailService) {
        this.responssableDeStageRepository = responssableDeStageRepository;
        this.joinTableCompteRepository = joinTableCompteRepository;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void addResponssableDeStage(@RequestBody NewResponssableRequest request) {
        ResponssableDeStage responssableDeStage = new ResponssableDeStage();
        responssableDeStage.setNom(request.nom());
        responssableDeStage.setPrenom(request.prenom());
        responssableDeStage.setEmail(request.email());
        responssableDeStage.setPassWord(encoder.encode(request.passWord()));
        responssableDeStageRepository.save(responssableDeStage);
        joinTableCompteRepository.save(JoinTableCompte.builder().rsd(responssableDeStage).build());
        emailService.sendAccountInfo(request.prenom, request.email, request.passWord);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ResponssableDeStage> getAllResponssablesDeStage() {
        return responssableDeStageRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','RDS')")
    @GetMapping("{resp_id}")
    public ResponssableDeStage getResponssableDeStageById(@PathVariable("resp_id") Integer id){
        return responssableDeStageRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','RDS')")
    @GetMapping("{resp_id}/etudiant")
    public List<Etudiant> getEtudiantByIdEncadrant(@PathVariable("resp_id") Integer id){
        ResponssableDeStage responssableDeStage = responssableDeStageRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return new ArrayList<>(responssableDeStage.getEtudiantsSuperviser());
    }

    @PreAuthorize("hasRole('RDS')")
    @GetMapping("{resp_id}/reunion")
    public List<Reunion> getReunionByIdEncadrant(@PathVariable("resp_id") Integer id){
        ResponssableDeStage responssableDeStage = responssableDeStageRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        List<Reunion> sortedReunion = new ArrayList<>(responssableDeStage.getReunions());
        sortedReunion.sort(Comparator.comparing(Reunion::getDate).reversed());
        return sortedReunion;
    }
    @PreAuthorize("hasAnyRole('ADMIN','RDS')")
    @PutMapping("{resp_id}")
    public void updateResponssableDeStage(@PathVariable("resp_id") Integer id, @RequestBody NewResponssableRequest request) {
        responssableDeStageRepository.findById(id)
                .map(responssableDeStage -> {
                            responssableDeStage.setNom(request.nom());
                            responssableDeStage.setPrenom(request.prenom());
                            responssableDeStage.setEmail(request.email());
                            responssableDeStage.setPassWord(request.passWord());
                            return responssableDeStageRepository.save(responssableDeStage);
                        }
                );
        //TODO:Add case of recieving an invalid id
    }

    @PreAuthorize("hasAnyRole('ADMIN','RDS')")
    @DeleteMapping("{resp_id}")
    public void deleteResponssableDeStage(@PathVariable("resp_id") Integer id){
        responssableDeStageRepository.deleteById(id);
    }

    record NewResponssableRequest(
            String nom,
            String prenom,
            String email,
            String passWord
    ){}
}
