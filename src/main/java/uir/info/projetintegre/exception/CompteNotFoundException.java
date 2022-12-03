package uir.info.projetintegre.exception;

public class CompteNotFoundException extends RuntimeException{

    public CompteNotFoundException(Integer id){
        super("could not find compte with id : "+id);
    }
}
