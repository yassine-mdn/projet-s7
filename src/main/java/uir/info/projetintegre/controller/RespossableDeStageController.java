package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.Professeur;
import uir.info.projetintegre.model.ResponssableDeStage;
import uir.info.projetintegre.model.Reunion;
import uir.info.projetintegre.repository.ResponssableDeStageRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/responssable")
public class RespossableDeStageController {

    private final ResponssableDeStageRepository responssableDeStageRepository;

    public RespossableDeStageController(ResponssableDeStageRepository responssableDeStageRepository) {
        this.responssableDeStageRepository = responssableDeStageRepository;
    }

    @PostMapping
    public void addResponssableDeStage(@RequestBody NewResponssableRequest request) {
        ResponssableDeStage responssableDeStage = new ResponssableDeStage();
        responssableDeStage.setNom(request.nom());
        responssableDeStage.setPrenom(request.prenom());
        responssableDeStage.setEmail(request.email());
        responssableDeStage.setPassWord(request.passWord());
        responssableDeStage.setReunions(request.reunions());
        responssableDeStage.setEtudiantsSuperviser(request.etudiantsSuperviser());
        responssableDeStageRepository.save(responssableDeStage);
    }

    @GetMapping
    public List<ResponssableDeStage> getAllResponssablesDeStage() {
        return responssableDeStageRepository.findAll();
    }

    @GetMapping("id={resp_id}")
    public ResponssableDeStage getResponssableDeStageById(@PathVariable("resp_id") Integer id){
        return responssableDeStageRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @GetMapping("id={resp_id}/etudiant")
    public List<Etudiant> getEtudiantByIdEncadrant(@PathVariable("resp_id") Integer id){
        ResponssableDeStage responssableDeStage = responssableDeStageRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return new ArrayList<>(responssableDeStage.getEtudiantsSuperviser());
    }

    @GetMapping("id={resp_id}/reunion")
    public List<Reunion> getReunionByIdEncadrant(@PathVariable("resp_id") Integer id){
        ResponssableDeStage responssableDeStage = responssableDeStageRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        List<Reunion> sortedReunion = new ArrayList<>(responssableDeStage.getReunions());
        sortedReunion.sort(Comparator.comparing(Reunion::getDate).reversed());
        return sortedReunion;
    }

    @PutMapping("id={resp_id}")
    public void updateResponssableDeStage(@PathVariable("resp_id") Integer id, @RequestBody NewResponssableRequest request) {
        responssableDeStageRepository.findById(id)
                .map(responssableDeStage -> {
                            responssableDeStage.setNom(request.nom());
                            responssableDeStage.setPrenom(request.prenom());
                            responssableDeStage.setEmail(request.email());
                            responssableDeStage.setPassWord(request.passWord());
                            responssableDeStage.setReunions(request.reunions());
                            responssableDeStage.setEtudiantsSuperviser(request.etudiantsSuperviser());
                            return responssableDeStageRepository.save(responssableDeStage);
                        }
                );
        //TODO:Add case of recieving an invalid id
    }

    @DeleteMapping("id={resp_id}")
    public void deleteResponssableDeStage(@PathVariable("resp_id") Integer id){
        responssableDeStageRepository.deleteById(id);
    }

    record NewResponssableRequest(
            String nom,
            String prenom,
            String email,
            String passWord,
            Set<Reunion>reunions,
            Set<Etudiant> etudiantsSuperviser
    ){}
}
