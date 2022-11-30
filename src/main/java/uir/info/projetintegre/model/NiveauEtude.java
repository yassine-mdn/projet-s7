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
@Table(name = "niveau_etude")
public class NiveauEtude {

    @Id
    @SequenceGenerator(
            name = "niveau_etude_id_sequence",
            sequenceName = "niveau_etude_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "niveau_etude_id_sequence"
    )
    private Integer idNE;

    private String libelle;

    @OneToMany(mappedBy = "niveauEtude")
    private Set<Etudiant> etudiants;

    @ManyToMany(mappedBy = "niveauEnseigners")
    private Set<Professeur> professeurs;

    @ManyToOne
    @JoinColumn(name="id_programme", nullable=false)
    private Programme programme;
}
