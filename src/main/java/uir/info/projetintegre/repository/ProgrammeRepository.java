package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Programme;

public interface ProgrammeRepository extends JpaRepository<Programme,Integer> {

    Programme findByNom(String nom);

}
