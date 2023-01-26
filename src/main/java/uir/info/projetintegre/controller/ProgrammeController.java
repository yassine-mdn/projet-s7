package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.ReunionNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.Programme;
import uir.info.projetintegre.repository.EtablissementRepository;
import uir.info.projetintegre.repository.ProgrammeRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/programmes")
public class ProgrammeController {

    private final ProgrammeRepository programmeRepository;
    private final EtablissementRepository etablissementRepository;

    public ProgrammeController(ProgrammeRepository programmeRepository, EtablissementRepository etablissementRepository) {
        this.programmeRepository = programmeRepository;
        this.etablissementRepository = etablissementRepository;
    }

    @PostMapping
    public void addProgramme(@RequestBody NewProgrammeRequest request) {
        Programme programme = new Programme();
        programme.setNom(request.nom());
        programme.setDescription(request.description());
        programme.setEtablissement(etablissementRepository.findById(request.idEtablissement).orElseThrow(() -> new ReunionNotFoundException(request.idEtablissement)));
        programmeRepository.save(programme);
    }

    @GetMapping
    public List<Programme> getAllProgrammes(){return programmeRepository.findAll();}

    @GetMapping("{programme_id}")
    public Programme getProgrammeById(@PathVariable("programme_id") Integer id) {
        return programmeRepository.findById(id).orElseThrow(() -> new ReunionNotFoundException(id));
    }

    @GetMapping("{programme_id}/etudiant")
    public List<Etudiant> getEtudiantsByIdProgramme(@PathVariable("programme_id") Integer id) {
        Programme programme = programmeRepository.findById(id).orElseThrow(() -> new ReunionNotFoundException(id));
        return new ArrayList<>(programme.getEtudiants());
    }

    @PutMapping("{programme_id}")
    public void updateProgramme(@PathVariable("programme_id") Integer id, @RequestBody NewProgrammeRequest request) {
        programmeRepository.findById(id)
                .map(programme -> {
                            programme.setNom(request.nom());
                            programme.setDescription(request.description());
                            return programmeRepository.save(programme);
                        }
                );
    }

    @DeleteMapping("{programme_id}")
    public void deleteProgramme(@PathVariable("programme_id") Integer id){
        programmeRepository.deleteById(id);
    }

    record NewProgrammeRequest(
            String nom,
            String description,
            Integer idEtablissement
    ) {
    }
}
