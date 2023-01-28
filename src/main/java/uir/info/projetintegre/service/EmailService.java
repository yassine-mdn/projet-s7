package uir.info.projetintegre.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uir.info.projetintegre.model.EmailDetails;
import uir.info.projetintegre.repository.EmailRepository;

@Service
public class EmailService implements EmailRepository {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    // Method 1
    // To send a simple email
    public String sendSimpleMail(EmailDetails details)
    {
        // Try block to check for exceptions

        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            new Thread(() -> {
                javaMailSender.send(mailMessage);
            }).start();
            // Sending the mail

            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String sendAccountInfo(String prenom, String email, String passWord){
        String msg = "Bonjour "+prenom+"\n\nVoici vos Information de Connection:\n\t-email: "+email+"\n\t-mot de passe: "+passWord;
        EmailDetails emailDetails =new EmailDetails(email,msg,"Information de connection projet S7");
        return sendSimpleMail(emailDetails);
    }

}
