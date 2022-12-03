package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant,Integer> {
}
