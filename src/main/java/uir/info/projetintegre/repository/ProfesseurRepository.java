package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Professeur;

import java.util.Optional;

public interface ProfesseurRepository extends JpaRepository<Professeur,Integer> {

    Optional<Professeur> findByEmail(String email);
}
