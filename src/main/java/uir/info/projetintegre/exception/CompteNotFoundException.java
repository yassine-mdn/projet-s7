package uir.info.projetintegre.exception;

public class AdminNotFoundException extends RuntimeException{

    AdminNotFoundException(Integer id){
        super("could not find admin with id : "+id);
    }
}
