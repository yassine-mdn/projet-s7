package uir.info.projetintegre.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uir.info.projetintegre.exception.CompteNotFoundException;
import uir.info.projetintegre.model.Fichier;
import uir.info.projetintegre.model.JoinTableCompte;
import uir.info.projetintegre.repository.FichierRepository;
import uir.info.projetintegre.repository.JoinTableCompteRepository;
import uir.info.projetintegre.service.StockageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/fichiers")
public class FichierController {

    private final StockageService stockageService;
    private final FichierRepository fichierRepository;
    private final JoinTableCompteRepository joinTableCompteRepository;


    public FichierController(StockageService stockageService, FichierRepository fichierRepository, JoinTableCompteRepository joinTableCompteRepository) {
        this.stockageService = stockageService;
        this.fichierRepository = fichierRepository;
        this.joinTableCompteRepository = joinTableCompteRepository;
    }

    @PostMapping("send/src/{src_id}/dest/{dest_id}")
    public ResponseEntity<?> sendFichier(@PathVariable("src_id") Integer src, @RequestParam("file") MultipartFile fichier, @PathVariable("dest_id") Integer dest) throws IOException {
        JoinTableCompte source = joinTableCompteRepository.findById(src).orElseThrow(() -> new CompteNotFoundException(src));
        JoinTableCompte destination = joinTableCompteRepository.findById(dest).orElseThrow(() -> new CompteNotFoundException(dest));
        String uploadFichier = stockageService.uploadFichier(fichier,source,destination);
        return ResponseEntity.status(HttpStatus.OK).body(uploadFichier);
    }

    @GetMapping("src/{src_id}")
    public List<Fichier> getSentFichier(@PathVariable("src_id") Integer src){
        JoinTableCompte source = joinTableCompteRepository.findById(src).orElseThrow(() -> new CompteNotFoundException(src));
        return new ArrayList<>(source.getSent());
    }

    @GetMapping("dest/{dest_id}")
    public List<Fichier> getReceivedFichier(@PathVariable("dest_id") Integer dest){
        JoinTableCompte destination = joinTableCompteRepository.findById(dest).orElseThrow(() -> new CompteNotFoundException(dest));
        return new ArrayList<>(destination.getReceived());
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
