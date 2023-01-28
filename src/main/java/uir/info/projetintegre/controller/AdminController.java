package uir.info.projetintegre.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.model.Admin;
import uir.info.projetintegre.model.JoinTableCompte;
import uir.info.projetintegre.repository.AdminRepository;
import uir.info.projetintegre.repository.JoinTableCompteRepository;
import uir.info.projetintegre.service.EmailService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminRepository adminRepository;
    private final JoinTableCompteRepository joinTableCompteRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    public AdminController(AdminRepository adminRepository, JoinTableCompteRepository joinTableCompteRepository, PasswordEncoder encoder, EmailService emailService) {
        this.adminRepository = adminRepository;
        this.joinTableCompteRepository = joinTableCompteRepository;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void addAdmin(@RequestBody NewAdminRequest request) {
        Admin admin = new Admin();
        admin.setNom(request.nom());
        admin.setPrenom(request.prenom());
        admin.setEmail(request.email());
        admin.setPassWord(encoder.encode(request.passWord()));
        adminRepository.save(admin);
        joinTableCompteRepository.save(JoinTableCompte.builder().admin(admin).build());
        emailService.sendAccountInfo(request.prenom, request.email, request.passWord);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{admin_id}")
    public Admin getAdminById(@PathVariable("admin_id") Integer id){
        return adminRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{admin_id}")
    public void updateAdmin(@PathVariable("admin_id") Integer id, @RequestBody NewAdminRequest request) {
        adminRepository.findById(id)
                .map(admin -> {
                            admin.setNom(request.nom());
                            admin.setPrenom(request.prenom());
                            admin.setEmail(request.email());
                            admin.setPassWord(request.passWord());
                            return adminRepository.save(admin);
                        }
                );
        //TODO:Add case of recieving an invalid id
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{admin_id}")
    public void deleteAdmin(@PathVariable("admin_id") Integer id){

        adminRepository.deleteById(id);
    }


    record NewAdminRequest(
            String nom,
            String prenom,
            String email,
            String passWord
    ) {
    }
}
