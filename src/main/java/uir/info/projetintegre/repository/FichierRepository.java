package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Fichier;

public interface FichierRepository extends JpaRepository<Fichier,Integer> {

    boolean existsByNom(String nom);
}
