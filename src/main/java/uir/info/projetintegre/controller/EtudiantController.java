package uir.info.projetintegre.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.model.*;
import uir.info.projetintegre.repository.EtudiantRepository;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/etudiant")
public class EtudiantController {

    private final EtudiantRepository etudiantRepository;

    public EtudiantController(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @PostMapping
    public void addEtudiant(@RequestBody NewEtudiantRequest request) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(request.nom());
        etudiant.setPrenom(request.prenom());
        etudiant.setEmail(request.email());
        etudiant.setPassWord(request.passWord());
        etudiant.setProfesseur(request.professeur());
        etudiant.setNiveauEtude(request.niveauEtude());
        etudiant.setSuperviseur(request.superviseur());
        etudiantRepository.save(etudiant);
    }

    //TODO: USe Pagination to get only 25 items at a time (saving resources)

    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @GetMapping("{etudiant_id}")
    public Etudiant getOneEtudiant(@PathVariable("etudiant_id") Integer id) {
        return etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @PutMapping("{etudiant_id}")
    public void updateEtudiant(@PathVariable("etudiant_id") Integer id, @RequestBody NewEtudiantRequest request) {
        etudiantRepository.findById(id)
                .map(etudiant -> {
                            etudiant.setNom(request.nom());
                            etudiant.setPrenom(request.prenom());
                            etudiant.setEmail(request.email());
                            etudiant.setPassWord(request.passWord());
                            etudiant.setProfesseur(request.professeur());
                            etudiant.setNiveauEtude(request.niveauEtude());
                            etudiant.setSuperviseur(request.superviseur());
                            return etudiantRepository.save(etudiant);
                        }
                );
        //TODO:Add case of recieving an invalid id
    }

    @DeleteMapping("{etudiant_id}")
    public void deleteEtudiant(@PathVariable("etudiant_id") Integer id) {
        etudiantRepository.deleteById(id);
    }

    record NewEtudiantRequest(
            String nom,
            String prenom,
            String email,
            String passWord,
            Professeur professeur,
            NiveauEtude niveauEtude,
            ResponssableDeStage superviseur
    ) {
    }
}
