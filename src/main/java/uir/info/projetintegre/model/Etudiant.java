package uir.info.projetintegre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Etudiant extends Compte{

    @ManyToMany
    @JoinTable(
            name = "participe",
            joinColumns = @JoinColumn(name = "id_etudiant"),
            inverseJoinColumns = @JoinColumn(name = "id_reunion")
    )
    private Set<Reunion> reunions;

    @ManyToOne
    @JoinColumn(name = "id_encadrant",nullable = false)
    private Professeur professeur;

    @ManyToOne
    @JoinColumn(name = "id_niveau_etude",nullable = false)
    private NiveauEtude niveauEtude;

    @ManyToOne
    @JoinColumn(name = "id_superviseur",nullable = false)
    private ResponssableDeStage superviseur;

    //TODO: Nzid l blan mta3 date debut stage & durrée
}
