package uir.info.projetintegre.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Etudiant extends Compte{

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
    @JsonIgnore
    @JoinColumn(name = "id_encadrant",nullable = false)
    private Professeur professeur;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_programme",nullable = false)
    private Programme programme;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_superviseur",nullable = false)
    private ResponssableDeStage superviseur;

    //TODO: Nzid l blan mta3 date debut stage & durrée
}
