package uir.info.projetintegre.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uir.info.projetintegre.model.Compte;
import uir.info.projetintegre.model.SecurityUser;
import uir.info.projetintegre.repository.AdminRepository;
import uir.info.projetintegre.repository.EtudiantRepository;
import uir.info.projetintegre.repository.ProfesseurRepository;
import uir.info.projetintegre.repository.ResponssableDeStageRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class CompteService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final ResponssableDeStageRepository responssableDeStageRepository;
    private final ProfesseurRepository professeurRepository;
    private  final EtudiantRepository etudiantRepository;

    public CompteService(AdminRepository adminRepository, ResponssableDeStageRepository responssableDeStageRepository, ProfesseurRepository professeurRepository, EtudiantRepository etudiantRepository) {
        this.adminRepository = adminRepository;
        this.responssableDeStageRepository = responssableDeStageRepository;
        this.professeurRepository = professeurRepository;
        this.etudiantRepository = etudiantRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (adminRepository.findByEmail(username).isPresent())
            return adminRepository.findByEmail(username).map(SecurityUser::new).orElseThrow();
        if (responssableDeStageRepository.findByEmail(username).isPresent())
            return responssableDeStageRepository.findByEmail(username).map(SecurityUser::new).orElseThrow();
        if (professeurRepository.findByEmail(username).isPresent())
            return professeurRepository.findByEmail(username).map(SecurityUser::new).orElseThrow();
        else
            return etudiantRepository.findByEmail(username).map(SecurityUser::new).orElseThrow(()-> new UsernameNotFoundException("user not found : " + username));
    }
}
