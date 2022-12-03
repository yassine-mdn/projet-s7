package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Professeur;

public interface ProfesseurRepository extends JpaRepository<Professeur,Integer> {
}
