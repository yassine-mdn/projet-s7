package uir.info.projetintegre.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uir.info.projetintegre.model.enums.Roles;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private Integer id;
    private String nom;
    private String prenom;
    private Roles role;
    private String token;
}
