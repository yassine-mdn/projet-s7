package uir.info.projetintegre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uir.info.projetintegre.model.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Optional<Admin> findByEmail(String email);
}
