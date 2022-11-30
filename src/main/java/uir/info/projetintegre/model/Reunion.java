package uir.info.projetintegre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reunion")
public class Reunion {

    @Id
    @SequenceGenerator(
            name = "reunion_id_sequence",
            sequenceName = "reunion_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reunion_id_sequence"
    )
    private Integer idR;

    private Date date;
    private String sujet;
    private Integer durreeEnMin;

    @ManyToOne
    @JoinColumn(name = "id_responssable",nullable = false)
    private ResponssableDeStage responssableDeStage;

    @ManyToMany(mappedBy = "reunions")
    private Set<Etudiant> etudiants;

}
