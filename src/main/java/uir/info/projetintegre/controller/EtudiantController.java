package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.model.*;
import uir.info.projetintegre.repository.EtudiantRepository;
import uir.info.projetintegre.repository.JoinTableCompteRepository;
import uir.info.projetintegre.repository.ProfesseurRepository;
import uir.info.projetintegre.repository.ResponssableDeStageRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/etudiant")
public class EtudiantController {

    private final EtudiantRepository etudiantRepository;
    private final ProfesseurRepository professeurRepository;
    private final ResponssableDeStageRepository responssableDeStageRepository;
    private final JoinTableCompteRepository joinTableCompteRepository;


    public EtudiantController(EtudiantRepository etudiantRepository,
                              ProfesseurRepository professeurRepository,
                              ResponssableDeStageRepository responssableDeStageRepository, JoinTableCompteRepository joinTableCompteRepository) {
        this.etudiantRepository = etudiantRepository;
        this.professeurRepository = professeurRepository;
        this.responssableDeStageRepository = responssableDeStageRepository;
        this.joinTableCompteRepository = joinTableCompteRepository;
    }

    @PostMapping
    public void addEtudiant(@RequestBody NewEtudiantRequest request) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(request.nom());
        etudiant.setPrenom(request.prenom());
        etudiant.setEmail(request.email());
        etudiant.setPassWord(request.passWord());
        etudiant.setNiveauEtude(request.niveauEtude());
        etudiantRepository.save(etudiant);
        joinTableCompteRepository.save(JoinTableCompte.builder().etudiant(etudiant).build());
    }


    //TODO: USe Pagination to get only 25 items at a time (saving resources) (todo 7itach fiha tamara bezzaf)
    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @GetMapping("id={etudiant_id}")
    public Etudiant getEtudiantById(@PathVariable("etudiant_id") Integer id) {
        return etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @GetMapping("id={etudiant_id}/encadrant")
    public Professeur getProfesseurByIdEtudiant(@PathVariable("etudiant_id") Integer id) {
        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return etudiant.getProfesseur();
    }

    @GetMapping("id={etudiant_id}/superviseur")
    public ResponssableDeStage getRDSByIdEtudiant(@PathVariable("etudiant_id") Integer id) {
        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        return etudiant.getSuperviseur();
    }

    @GetMapping("id={etudiant_id}/reunion")
    public List<Reunion> getReunionByIdEtudiant(@PathVariable("etudiant_id") Integer id){
        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
        List<Reunion> sortedReunion = new ArrayList<>(etudiant.getReunions());
        sortedReunion.sort(Comparator.comparing(Reunion::getDate).reversed());
        return sortedReunion;
    }

    @PutMapping("id={etudiant_id}")
    public void updateEtudiant(@PathVariable("etudiant_id") Integer id, @RequestBody NewEtudiantRequest request) {
        etudiantRepository.findById(id)
                .map(etudiant -> {
                            etudiant.setNom(request.nom());
                            etudiant.setPrenom(request.prenom());
                            etudiant.setEmail(request.email());
                            etudiant.setPassWord(request.passWord());
                            etudiant.setNiveauEtude(request.niveauEtude());
                            return etudiantRepository.save(etudiant);
                        }
                );
        //TODO:Add case of recieving an invalid id
    }

    //ineficace mais c plus facile a implementé hh
    @PutMapping("id={etudiant_id}/encadrant")
    public void setEtudiantEncadrant(@PathVariable("etudiant_id") Integer id, @RequestBody Professeur request) {

        etudiantRepository.findById(id)
                .map(etudiant -> {
                            etudiant.setProfesseur(professeurRepository.findById(request.getIdC())
                                    .orElseThrow(() -> new CompteNotFoundException(id)));
                            return etudiantRepository.save(etudiant);
                        }
                );
    }

    @PutMapping("id={etudiant_id}/superviseur")
    public void setEtudiantSuperviseur(@PathVariable("etudiant_id") Integer id, @RequestBody ResponssableDeStage request) {

        etudiantRepository.findById(id)
                .map(etudiant -> {
                            etudiant.setSuperviseur(responssableDeStageRepository.findById(request.getIdC())
                                    .orElseThrow(() -> new CompteNotFoundException(id)));
                            return etudiantRepository.save(etudiant);
                        }
                );
    }

    @DeleteMapping("id={etudiant_id}")
    public void deleteEtudiant(@PathVariable("etudiant_id") Integer id) {
        etudiantRepository.deleteById(id);
    }

    record NewEtudiantRequest(
            String nom,
            String prenom,
            String email,
            String passWord,
            Integer niveauEtude
    ) {
    }
}
