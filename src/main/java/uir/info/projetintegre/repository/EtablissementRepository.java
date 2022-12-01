package uir.info.projetintegre.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Etablissement;

public interface EtablissementRepository extends JpaRepository<Etablissement,Integer> {
}
