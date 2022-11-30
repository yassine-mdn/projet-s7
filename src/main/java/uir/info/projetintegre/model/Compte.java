package uir.info.projetintegre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Compte {

    @Id
    @SequenceGenerator(
            name = "compte_id_sequence",
            sequenceName = "compte_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "compte_id_sequence"
    )
    private Integer idC;
    private String nom;
    private String prenom;
    private String passWord;


}
