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
public class ResponssableDeStage extends Compte{

    @OneToMany(mappedBy = "responssableDeStage")
    @JsonIgnore
    private Set<Reunion> reunions;

    @OneToMany(mappedBy = "superviseur")
    @JsonIgnore
    private Set<Etudiant> etudiantsSuperviser;

}
