package uir.info.projetintegre.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uir.info.projetintegre.model.enums.Roles;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Compte implements UserDetails {

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
    @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passWord;

    @Enumerated(value = EnumType.STRING)
    private Roles role;
    @OneToMany(mappedBy = "receiver",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Fichier> received;

    @OneToMany(mappedBy = "sender",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Fichier> sent;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
