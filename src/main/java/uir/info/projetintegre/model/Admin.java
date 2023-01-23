package uir.info.projetintegre.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
public class Admin extends Compte{

    @OneToOne(mappedBy = "admin",cascade = CascadeType.ALL)
    @JsonIgnore
    private JoinTableCompte joinTableCompte;
}
