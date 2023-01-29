package uir.info.projetintegre.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.auth.AuthenticationRequest;
import uir.info.projetintegre.auth.AuthenticationResponse;
import uir.info.projetintegre.auth.RegisterRequest;
import uir.info.projetintegre.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/hi")
    public ResponseEntity<String> hi(){
        return ResponseEntity.ok("hi"); //just to test
    }

}