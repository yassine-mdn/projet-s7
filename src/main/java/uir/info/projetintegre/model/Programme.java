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
@Table(name = "programme")
public class Programme {

    @Id
    @SequenceGenerator(
            name = "programme_id_sequence",
            sequenceName = "programme_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "programme_id_sequence"
    )
    private Integer idProgramme;

    private String nom;
    private String description;

    @OneToMany(mappedBy = "programme",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Etudiant> etudiants;

    @OneToMany(mappedBy = "programme",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Professeur> professeurs;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_etablissement", nullable = false)
    private Etablissement etablissement;
}
