package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.Professeur;
import uir.info.projetintegre.repository.ProfesseurRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/professeur")
public class ProfesseurController {

    private final ProfesseurRepository professeurRepository;

    public ProfesseurController(ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
    }


    @PostMapping
    public void addProfesseur(@RequestBody NewProfesseurRequest request) {
        Professeur professeur = new Professeur();
        professeur.setNom(request.nom());
        professeur.setPrenom(request.prenom());
        professeur.setEmail(request.email());
        professeur.setPassWord(request.passWord());
        professeur.setEtudiants(request.etudiants());
        professeurRepository.save(professeur);
    }

    @GetMapping
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }

    @GetMapping("id={prof_id}")
    public Professeur getProfesseurById(@PathVariable("prof_id") Integer id){
        return professeurRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @GetMapping("id={prof_id}/etudiant")
    public List<Etudiant> getEtudiantByIdProfesseur(@PathVariable("prof_id") Integer id){
        Professeur professeur = professeurRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return new ArrayList<>(professeur.getEtudiants());
    }

    @PutMapping("id={prof_id}")
    public void updateProfesseur(@PathVariable("prof_id") Integer id, @RequestBody NewProfesseurRequest request) {
        professeurRepository.findById(id)
                .map(professeur -> {
                            professeur.setNom(request.nom());
                            professeur.setPrenom(request.prenom());
                            professeur.setEmail(request.email());
                            professeur.setPassWord(request.passWord());
                            professeur.setEtudiants(request.etudiants());
                            return professeurRepository.save(professeur);
                        }
                );
        //TODO:Add case of recieving an invalid id
    }

    @DeleteMapping("id={prof_id}")
    public void deleteProfesseur(@PathVariable("prof_id") Integer id){
        professeurRepository.deleteById(id);
    }

    record NewProfesseurRequest(
            String nom,
            String prenom,
            String email,
            String passWord,
            Set<Etudiant> etudiants

    ){}
}
