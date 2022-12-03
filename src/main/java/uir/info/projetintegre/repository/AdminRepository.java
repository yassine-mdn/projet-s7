package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
