package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Compte;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte,Integer> {
    Optional<Compte> findByEmail(String email);
}
