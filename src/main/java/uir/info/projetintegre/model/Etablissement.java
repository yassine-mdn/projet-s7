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
@Table(name = "etablissement")
public class Etablissement {

    @Id
    @SequenceGenerator(
            name = "etablissement_id_sequence",
            sequenceName = "etablissement_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "etablissement_id_sequence"
    )
    private Integer idEta;

    private String nom;

    @OneToMany(mappedBy = "etablissement")
    private Set<Programme> programmes;

}
