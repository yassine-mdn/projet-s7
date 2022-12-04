package uir.info.projetintegre.security;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uir.info.projetintegre.model.Fichier;
import uir.info.projetintegre.repository.FichierRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Optional;

@Service
public class StockageService {

    private final FichierRepository fichierRepository;

    public StockageService(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    private final String CHEMIN_REPERTOIRE ="C:/Users/yassi/Desktop/test-file-transfer/";

    public String uploadFichier(MultipartFile file) throws IOException {
        String cheminComplet= CHEMIN_REPERTOIRE +file.getOriginalFilename();

        long millis = System.currentTimeMillis();

        if(!fichierRepository.existsByNom(file.getOriginalFilename())) {

            Fichier fileData = fichierRepository.save(Fichier.builder()
                    .nom(file.getOriginalFilename())
                    .type(file.getContentType())
                    .dateUpload(new Date(millis))
                    .cheminFichier(cheminComplet).build());


            file.transferTo(new File(cheminComplet));

            return "file uploaded successfully : " + cheminComplet;
        }
        file.transferTo(new File(cheminComplet));
        return "file modified successfully : " + cheminComplet;
    }

    public byte[] downloadFichier(Integer fichierId) throws IOException {
        Optional<Fichier> infoFichier = fichierRepository.findById(fichierId);
        String filePath= infoFichier.get().getCheminFichier();
        byte[] fichier = Files.readAllBytes(new File(filePath).toPath());
        return fichier;
    }
}
