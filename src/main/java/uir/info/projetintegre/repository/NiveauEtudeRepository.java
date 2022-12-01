package uir.info.projetintegre.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.NiveauEtude;

public interface NiveauEtudeRepository extends JpaRepository<NiveauEtude,Integer> {
}
