package uir.info.projetintegre.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ADMIN,RDS,PROF,STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}

