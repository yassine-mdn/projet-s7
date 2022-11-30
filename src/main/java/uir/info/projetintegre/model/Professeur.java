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
public class Professeur extends Compte{

    @OneToMany(mappedBy = "professeur")
    private Set<Etudiant> etudiants;

    @ManyToMany
    @JoinTable(
            name = "enseigne",
            joinColumns = @JoinColumn(name = "id_professeur"),
            inverseJoinColumns = @JoinColumn(name = "id_niveau")
    )
    private Set<NiveauEtude> niveauEnseigners;
}
