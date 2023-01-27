package uir.info.projetintegre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uir.info.projetintegre.model.*;
import uir.info.projetintegre.repository.*;

import java.util.Arrays;
import java.util.Set;

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
            adminRepository.save(admin);
            ResponssableDeStage rds = new ResponssableDeStage();
            rds.setEmail("test@gmail.com");
            rds.setPassWord(encoder.encode("test"));
            responssableDeStageRepository.save(rds);
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
