package uir.info.projetintegre.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String titre;
    private String description;
    private Integer durreeEnMin;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "id_responssable",nullable = false)
    private ResponssableDeStage responssableDeStage;

    @ManyToMany(mappedBy = "reunions")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Etudiant> etudiants;

}
