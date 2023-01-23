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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "idc")
    private Admin admin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_id", referencedColumnName = "idc")
    private Professeur professeur;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", referencedColumnName = "idc")
    private Etudiant etudiant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rsd_id", referencedColumnName = "idc")
    private ResponssableDeStage rsd;

    @OneToMany(mappedBy = "receiver",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Fichier> received;

    @OneToMany(mappedBy = "sender",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Fichier> sent;
}
