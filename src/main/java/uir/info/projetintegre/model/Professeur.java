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

    @OneToOne
    @JoinColumn(name = "id_prog",referencedColumnName = "idProgramme")
    private Programme programme;

    @OneToOne(mappedBy = "professeur",cascade = CascadeType.ALL)
    @JsonIgnore
    private JoinTableCompte joinTableCompte;

}
