package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Etudiant;

import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant,Integer> {

    Optional<Etudiant> findByEmail(String email);
}
