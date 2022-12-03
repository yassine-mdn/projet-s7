package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.ReunionNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.ResponssableDeStage;
import uir.info.projetintegre.model.Reunion;
import uir.info.projetintegre.repository.ReunionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/reunion")
public class ReunionController {

    private final ReunionRepository reunionRepository;

    public ReunionController(ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    @PostMapping
    public void addReunion(@RequestBody NewReunionRequest request) {
        Reunion reunion = new Reunion();
        reunion.setDate(request.date());
        reunion.setTitre(request.titre());
        reunion.setDescription(request.description());
        reunion.setDurreeEnMin(request.durreeEnMin());
        reunion.setResponssableDeStage(request.responssableDeStage());
        reunion.setEtudiants(request.etudiants());
        reunionRepository.save(reunion);
    }

    @GetMapping("id={reunion_id}")
    public Reunion getReunionById(@PathVariable("reunion_id") Integer id) {
        return reunionRepository.findById(id).orElseThrow(() -> new ReunionNotFoundException(id));
    }

    @GetMapping("id={reunion_id}/etudiant")
    public List<Etudiant> getEtudiantByIdReunion(@PathVariable("reunion_id") Integer id) {
        Reunion reunion = reunionRepository.findById(id).orElseThrow(() -> new ReunionNotFoundException(id));
        return new ArrayList<>(reunion.getEtudiants());
    }

    @PutMapping("id={reunion_id}")
    public void updateReunion(@PathVariable("reunion_id") Integer id, @RequestBody NewReunionRequest request) {
        reunionRepository.findById(id)
                .map(reunion -> {
                            reunion.setDate(request.date());
                            reunion.setTitre(request.titre());
                            reunion.setDescription(request.description());
                            reunion.setDurreeEnMin(request.durreeEnMin());
                            reunion.setEtudiants(request.etudiants());
                            return reunionRepository.save(reunion);
                        }
                );
    }

    @DeleteMapping("id={reunion_id}")
    public void deleteReunion(@PathVariable("reunion_id") Integer id){
        reunionRepository.deleteById(id);
    }

    record NewReunionRequest(
            Date date,
            String titre,
            String description,
            Integer durreeEnMin,
            ResponssableDeStage responssableDeStage,
            Set<Etudiant> etudiants
    ) {
    }
}
