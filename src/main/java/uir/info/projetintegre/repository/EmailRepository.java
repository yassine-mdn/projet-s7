package uir.info.projetintegre.repository;

import uir.info.projetintegre.model.EmailDetails;

public interface EmailRepository {
    String sendSimpleMail(EmailDetails details);

}
