package uir.info.projetintegre.service;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.JoinTableCompte;
import uir.info.projetintegre.model.Programme;
import uir.info.projetintegre.repository.EtudiantRepository;
import uir.info.projetintegre.repository.JoinTableCompteRepository;
import uir.info.projetintegre.repository.ProgrammeRepository;

import java.io.IOException;
@Service
public class BatchCreateService {

    public final JoinTableCompteRepository joinTableCompteRepository;
    public final ProgrammeRepository programmeRepository;
    public final EtudiantRepository etudiantRepository;
    private static final String[] HEADERS = { "id", "nom", "prenom", "email", "niveauEtude", "programme" };

    public BatchCreateService(JoinTableCompteRepository joinTableCompteRepository, ProgrammeRepository programmeRepository, EtudiantRepository etudiantRepository, PasswordEncoder encoder) {
        this.joinTableCompteRepository = joinTableCompteRepository;
        this.programmeRepository = programmeRepository;
        this.etudiantRepository = etudiantRepository;
        this.encoder = encoder;
    }

    final
    PasswordEncoder encoder;
    @SneakyThrows
    public String createEtudiantFromFile(MultipartFile excel){
        String message = "";
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Check if the header is a "Etudiant" header :
            XSSFRow header = sheet.getRow(0);
            for (int j = 0; j < header.getPhysicalNumberOfCells(); j++) {
                String headValue = header.getCell(j).getStringCellValue();
                // Check it with header :
                if (!HEADERS[j].equals(headValue)) {
                    workbook.close();
                    message = "Wrong header";
                    return message;
                }
            }

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheet.getRow(i);
                // Create Etudiant object:
                Etudiant etudiant = new Etudiant();
                etudiant.setNom(row.getCell(1).getStringCellValue());
                etudiant.setPrenom(row.getCell(2).getStringCellValue());
                etudiant.setEmail(row.getCell(3).getStringCellValue());
                etudiant.setNiveauEtude((int) row.getCell(4).getNumericCellValue());
                etudiant.setPassWord(encoder.encode(passwordGenerator.generate(8)));

                String programmeName = row.getCell(5).getStringCellValue();
                Programme programme = programmeRepository.findByNom(programmeName);

                if (programme == null) {
                    return "Wrong \"Programme\" name";
                }
                etudiant.setProgramme(programme);
                etudiantRepository.save(etudiant);
                joinTableCompteRepository.save(JoinTableCompte.builder().etudiant(etudiant).build());
            }
            workbook.close();
            return "Excel file successfully imported";

        } catch (IOException e) {
            message = e.getMessage();
            return message;
        }
    }
}
