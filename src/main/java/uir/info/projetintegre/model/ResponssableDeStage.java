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
public class ResponssableDeStage extends Compte{

    @OneToMany(mappedBy = "responssableDeStage")
    private Set<Reunion> reunions;

    @OneToMany(mappedBy = "superviseur")
    private Set<Etudiant> etudiantsSuperviser;

}
