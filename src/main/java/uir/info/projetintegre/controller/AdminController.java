package uir.info.projetintegre.controller;

import org.springframework.web.bind.annotation.*;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.model.Admin;
import uir.info.projetintegre.model.JoinTableCompte;
import uir.info.projetintegre.repository.AdminRepository;
import uir.info.projetintegre.repository.JoinTableCompteRepository;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminRepository adminRepository;
    private final JoinTableCompteRepository joinTableCompteRepository;

    public AdminController(AdminRepository adminRepository, JoinTableCompteRepository joinTableCompteRepository) {
        this.adminRepository = adminRepository;
        this.joinTableCompteRepository = joinTableCompteRepository;
    }

    @PostMapping
    public void addAdmin(@RequestBody NewAdminRequest request) {
        Admin admin = new Admin();
        admin.setNom(request.nom());
        admin.setPrenom(request.prenom());
        admin.setEmail(request.email());
        admin.setPassWord(request.passWord());
        adminRepository.save(admin);
        joinTableCompteRepository.save(JoinTableCompte.builder().admin(admin).build());
    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @GetMapping("id={admin_id}")
    public Admin getAdminById(@PathVariable("admin_id") Integer id){
        return adminRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    @PutMapping("id={admin_id}")
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

    @DeleteMapping("id={admin_id}")
    public void deleteAdmin(@PathVariable("admin_id") Integer id){

        adminRepository.deleteById(id);
        joinTableCompteRepository.deleteById(id);
    }


    record NewAdminRequest(
            String nom,
            String prenom,
            String email,
            String passWord
    ) {
    }
}
