package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.exception.ProgrammeNotFoundException;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.JoinTableCompte;
import uir.info.projetintegre.model.Professeur;
import uir.info.projetintegre.repository.JoinTableCompteRepository;
import uir.info.projetintegre.repository.ProfesseurRepository;
import uir.info.projetintegre.repository.ProgrammeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/professeurs")
public class ProfesseurController {

    private final ProfesseurRepository professeurRepository;
    private final JoinTableCompteRepository joinTableCompteRepository;
    private final ProgrammeRepository programmeRepository;


    public ProfesseurController(ProfesseurRepository professeurRepository, JoinTableCompteRepository joinTableCompteRepository, ProgrammeRepository programmeRepository) {
        this.professeurRepository = professeurRepository;
        this.joinTableCompteRepository = joinTableCompteRepository;
        this.programmeRepository = programmeRepository;
    }


    @PostMapping
    public void addProfesseur(@RequestBody NewProfesseurRequest request) {
        Professeur professeur = new Professeur();
        professeur.setNom(request.nom());
        professeur.setPrenom(request.prenom());
        professeur.setEmail(request.email());
        professeur.setPassWord(request.passWord());
        professeur.setProgramme(programmeRepository.findById(request.programmeId).orElseThrow(() -> new ProgrammeNotFoundException(request.programmeId)));
        professeur.setEtudiants(request.etudiants());
        professeurRepository.save(professeur);
        joinTableCompteRepository.save(JoinTableCompte.builder().professeur(professeur).build());
    }


    @GetMapping
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }

    @GetMapping("{prof_id}")
    public Professeur getProfesseurById(@PathVariable("prof_id") Integer id){
        return professeurRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @GetMapping("{prof_id}/etudiant")
    public List<Etudiant> getEtudiantByIdProfesseur(@PathVariable("prof_id") Integer id){
        Professeur professeur = professeurRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return new ArrayList<>(professeur.getEtudiants());
    }

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
