package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.ResponssableDeStage;

import java.util.Optional;

public interface ResponssableDeStageRepository extends JpaRepository<ResponssableDeStage,Integer> {
    Optional<ResponssableDeStage> findByEmail(String email);
}
