package uir.info.projetintegre.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uir.info.projetintegre.model.enums.Roles;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Etudiant extends Compte{

    static final Roles role = Roles.STUDENT;

    private Integer niveauEtude;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "participe",
            joinColumns = @JoinColumn(name = "id_etudiant"),
            inverseJoinColumns = @JoinColumn(name = "id_reunion")
    )
    private Set<Reunion> reunions;

    @ManyToOne
    @JoinColumn(name = "id_encadrant")
    private Professeur professeur;

    @ManyToOne
    @JoinColumn(name = "id_programme",nullable = false)
    private Programme programme;


    @ManyToOne
    @JoinColumn(name = "id_superviseur")
    private ResponssableDeStage superviseur;

    @OneToOne(mappedBy = "etudiant",cascade = CascadeType.ALL)
    @JsonIgnore
    private JoinTableCompte joinTableCompte;

    //TODO: Nzid l blan mta3 date debut stage & durrée
}
