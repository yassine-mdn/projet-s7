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
public class Professeur extends Compte{

    @OneToMany(mappedBy = "professeur")
    @JsonIgnore
    private Set<Etudiant> etudiants;

    @ManyToOne
    @JoinColumn(name = "id_programme",nullable = true)
    private Programme programme;

    @OneToOne(mappedBy = "professeur",cascade = CascadeType.ALL)
    @JsonIgnore
    private JoinTableCompte joinTableCompte;

}
