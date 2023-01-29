package uir.info.projetintegre;

import org.apache.catalina.Store;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uir.info.projetintegre.model.*;
import uir.info.projetintegre.model.enums.Roles;
import uir.info.projetintegre.repository.*;
import uir.info.projetintegre.service.EmailService;

import java.util.Arrays;

@SpringBootApplication
public class MyApplication{

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }



    @Bean
    CommandLineRunner commandLineRunner(AdminRepository adminRepository, ResponssableDeStageRepository responssableDeStageRepository, PasswordEncoder encoder){

        return args -> {
            Admin admin = new Admin();
            admin.setPrenom("yassine");
            admin.setNom("mouddene");
            admin.setEmail("yassinemouddene@gmail.com");
            admin.setPassWord(encoder.encode("yassine"));
            admin.setRole(Roles.ADMIN);
            adminRepository.save(admin);
            ResponssableDeStage rds = new ResponssableDeStage();
            rds.setEmail("test@gmail.com");
            rds.setPassWord(encoder.encode("test"));
            rds.setRole(Roles.RDS);
            responssableDeStageRepository.save(rds);
        };
    }


}
