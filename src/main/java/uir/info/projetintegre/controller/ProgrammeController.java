package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.ReunionNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.Programme;
import uir.info.projetintegre.repository.ProgrammeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/programme")
public class ProgrammeController {

    private final ProgrammeRepository programmeRepository;

    public ProgrammeController(ProgrammeRepository programmeRepository) {
        this.programmeRepository = programmeRepository;
    }

    @PostMapping
    public void addProgramme(@RequestBody NewProgrammeRequest request) {
        Programme programme = new Programme();
        programme.setNom(request.nom());
        programme.setDescription(request.description());
        programmeRepository.save(programme);
    }

    @GetMapping("id={programme_id}")
    public Programme getProgrammeById(@PathVariable("programme_id") Integer id) {
        return programmeRepository.findById(id).orElseThrow(() -> new ReunionNotFoundException(id));
    }

    @GetMapping("id={programme_id}/etudiant")
    public List<Etudiant> getEtudiantsByIdProgramme(@PathVariable("programme_id") Integer id) {
        Programme programme = programmeRepository.findById(id).orElseThrow(() -> new ReunionNotFoundException(id));
        return new ArrayList<>(programme.getEtudiants());
    }

    @PutMapping("id={programme_id}")
    public void updateProgramme(@PathVariable("programme_id") Integer id, @RequestBody NewProgrammeRequest request) {
        programmeRepository.findById(id)
                .map(programme -> {
                            programme.setNom(request.nom());
                            programme.setDescription(request.description());
                            return programmeRepository.save(programme);
                        }
                );
    }

    @DeleteMapping("id={programme_id}")
    public void deleteProgramme(@PathVariable("programme_id") Integer id){
        programmeRepository.deleteById(id);
    }

    record NewProgrammeRequest(
            String nom,
            String description
    ) {
    }
}
