package uir.info.projetintegre.exception;

public class ReunionNotFoundException extends RuntimeException{

    public ReunionNotFoundException(Integer id){
        super("could not find reunion with id : "+id);
    }
}
