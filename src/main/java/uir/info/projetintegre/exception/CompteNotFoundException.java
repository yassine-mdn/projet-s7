package uir.info.projetintegre.exception;

import java.util.List;

public class CompteNotFoundException extends RuntimeException {

    public CompteNotFoundException(Integer id) {
        super("could not find compte with id : " + id);
    }

    public CompteNotFoundException(List<Integer> ids) {
        super("could not find comptes with ids : " + ids.toString());
    }
}
