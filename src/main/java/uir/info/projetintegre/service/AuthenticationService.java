package uir.info.projetintegre.service;



import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uir.info.projetintegre.auth.AuthenticationRequest;
import uir.info.projetintegre.auth.AuthenticationResponse;
import uir.info.projetintegre.auth.RegisterRequest;
import uir.info.projetintegre.model.Compte;
import uir.info.projetintegre.model.enums.Roles;
import uir.info.projetintegre.repository.CompteRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CompteRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Compte.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .passWord(passwordEncoder.encode(request.getPassword()))
                .role(Roles.PROF)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getIdC())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getIdC())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }
}
