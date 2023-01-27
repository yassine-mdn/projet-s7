package uir.info.projetintegre.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.model.Compte;
import uir.info.projetintegre.service.CompteService;

import java.io.IOException;
import java.util.List;

@RestController("/api/v1/auth")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping("/success")
    public String  successPage(){
        return "success";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login page";
    }

    @GetMapping("/logout")
    public String logoutPage(){
        return "logout successful";
    }

}