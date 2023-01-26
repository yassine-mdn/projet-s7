package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.exception.ReunionNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.Reunion;
import uir.info.projetintegre.repository.EtudiantRepository;
import uir.info.projetintegre.repository.ResponssableDeStageRepository;
import uir.info.projetintegre.repository.ReunionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/reunions")
public class ReunionController {

    private final ReunionRepository reunionRepository;
    private final EtudiantRepository etudiantRepository;
    private final ResponssableDeStageRepository responssableDeStageRepository;

    public ReunionController(ReunionRepository reunionRepository, EtudiantRepository etudiantRepository, ResponssableDeStageRepository responssableDeStageRepository) {
        this.reunionRepository = reunionRepository;
        this.etudiantRepository = etudiantRepository;
        this.responssableDeStageRepository = responssableDeStageRepository;
    }

    @PostMapping
    public void addReunion(@RequestBody NewReunionRequest request) {
        Reunion reunion = new Reunion();
        reunion.setDate(request.date());
        reunion.setTitre(request.titre());
        reunion.setDescription(request.description());
        reunion.setDurreeEnMin(request.durreeEnMin());
        reunion.setResponssableDeStage( responssableDeStageRepository.findById(request.responsableId).orElseThrow(() -> new CompteNotFoundException(request.responsableId)));
        reunion.setEtudiants(Set.copyOf( etudiantRepository.findAllById(request.etudiantsId)));
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
                            reunion.setEtudiants(Set.copyOf(etudiantRepository.findAllById(request.etudiantsId)));
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
            Integer responsableId,
            List<Integer> etudiantsId
    ) {
    }
}
