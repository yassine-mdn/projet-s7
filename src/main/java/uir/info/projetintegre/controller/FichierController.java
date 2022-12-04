package uir.info.projetintegre.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uir.info.projetintegre.model.Fichier;
import uir.info.projetintegre.repository.FichierRepository;
import uir.info.projetintegre.security.StockageService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/fichier")
public class FichierController {

    private final StockageService stockageService;
    private final FichierRepository fichierRepository;

    public FichierController(StockageService stockageService, FichierRepository fichierRepository) {
        this.stockageService = stockageService;
        this.fichierRepository = fichierRepository;
    }


    @PostMapping()
    public ResponseEntity<?> addFichier(@RequestParam("file") MultipartFile fichier) throws IOException {

        String uploadFichier = stockageService.uploadFichier(fichier);
        return ResponseEntity.status(HttpStatus.OK).body(uploadFichier);
    }

    @GetMapping
    public List<Fichier> getAllFichierRecords(){
        return fichierRepository.findAll();
    }

    @GetMapping("/{fichier_id}")
    public ResponseEntity<?> getFichierById(@PathVariable("fichier_id") Integer id) throws IOException {
        byte[] fichierData=stockageService.downloadFichier(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(fichierData);

    }



    record NewFichierRecord(
            String nom,
            String type
    ){}
}
