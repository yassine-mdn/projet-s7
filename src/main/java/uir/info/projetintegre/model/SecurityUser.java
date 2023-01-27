package uir.info.projetintegre.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uir.info.projetintegre.model.enums.Roles;

import java.util.Arrays;
import java.util.Collection;

public class SecurityUser implements UserDetails {

    private Compte compte;

    public SecurityUser(Compte compte) {
        this.compte = compte;
    }


    @Override
    public String getUsername() {
        return compte.getEmail();
    }

    @Override
    public String getPassword() {
        return compte.getPassWord();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Roles roles = compte instanceof Admin ? Roles.ADMIN : compte instanceof ResponssableDeStage? Roles.RDS : compte instanceof Professeur ? Roles.PROF:Roles.STUDENT;
        return Arrays.stream(roles.getAuthority().split(""))
                .map(SimpleGrantedAuthority::new)
                .toList();
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
