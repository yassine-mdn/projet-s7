package uir.info.projetintegre.service;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uir.info.projetintegre.model.Etudiant;
import uir.info.projetintegre.model.Programme;
import uir.info.projetintegre.model.enums.Roles;
import uir.info.projetintegre.repository.EtudiantRepository;
import uir.info.projetintegre.repository.ProgrammeRepository;

import java.io.IOException;
@Service
public class BatchCreateService {

    private final ProgrammeRepository programmeRepository;
    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder encoder;
    private static final String[] HEADERS = { "id", "nom", "prenom", "email", "niveauEtude", "programme" };

    public BatchCreateService( ProgrammeRepository programmeRepository, EtudiantRepository etudiantRepository, PasswordEncoder encoder) {
        this.programmeRepository = programmeRepository;
        this.etudiantRepository = etudiantRepository;
        this.encoder = encoder;
    }


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
                String passWord =passwordGenerator.generate(8);
                etudiant.setPassWord(encoder.encode(passWord));

                String programmeName = row.getCell(5).getStringCellValue();
                Programme programme = programmeRepository.findByNom(programmeName);

                if (programme == null) {
                    return "Wrong \"Programme\" name";
                }
                etudiant.setProgramme(programme);
                etudiant.setRole(Roles.STUDENT);
                etudiantRepository.save(etudiant);
            }
            workbook.close();
            return "Excel file successfully imported";

        } catch (IOException e) {
            message = e.getMessage();
            return message;
        }
    }
}
