package uir.info.projetintegre.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class JoinTableCompte {

    @Id
    @SequenceGenerator(
            name = "join_compte_id_sequence",
            sequenceName = "join_compte_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "join_compte_id_sequence"
    )
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", referencedColumnName = "idc")
    private Admin admin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prof_id", referencedColumnName = "idc")
    private Professeur professeur;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "etudiant_id", referencedColumnName = "idc")
    private Etudiant etudiant;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rsd_id", referencedColumnName = "idc")
    private ResponssableDeStage rsd;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private Set<Fichier> received;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private Set<Fichier> sent;
}
