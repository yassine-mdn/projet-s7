package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.ReunionNotFoundException;
import uir.info.projetintegre.model.*;
import uir.info.projetintegre.repository.EtablissementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/etablissements")
public class EtablissementController {

    private final EtablissementRepository etablissementRepository;


    public EtablissementController(EtablissementRepository etablissementRepository) {
        this.etablissementRepository = etablissementRepository;
    }

    @PostMapping
    public void addEtablissement(@RequestBody NewEtablissementRequest request) {
        Etablissement etablissement = new Etablissement();
        etablissement.setNom(request.nom());
        etablissementRepository.save(etablissement);
    }

    @GetMapping
    public List<Etablissement> getAllEtablissements() {
        return etablissementRepository.findAll();
    }

    @GetMapping("{etablissement_id}")
    public Etablissement getEtablissementById(@PathVariable("etablissement_id") Integer id) {
        return etablissementRepository.findById(id).orElseThrow(() -> new ReunionNotFoundException(id));
    }

    @GetMapping("{etablissement_id}/programme")
    public List<Programme> getProgrammeByIdEtablissement(@PathVariable("etablissement_id") Integer id) {
        Etablissement etablissement = etablissementRepository.findById(id).orElseThrow(() -> new ReunionNotFoundException(id));
        return new ArrayList<>(etablissement.getProgrammes());
    }

    @PutMapping("{etablissement_id}")
    public void updateEtablissement(@PathVariable("etablissement_id") Integer id, @RequestBody NewEtablissementRequest request) {
        etablissementRepository.findById(id)
                .map(etablissement -> {
                            etablissement.setNom(request.nom());
                            return etablissementRepository.save(etablissement);
                        }
                );
    }

    @DeleteMapping("{etablissement_id}")
    public void deleteEtablissement(@PathVariable("etablissement_id") Integer id) {
        etablissementRepository.deleteById(id);
    }

    record NewEtablissementRequest(
            String nom
    ) {
    }
}
