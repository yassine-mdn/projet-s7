package uir.info.projetintegre.exception;

public class ProgrammeNotFoundException extends RuntimeException{

    public ProgrammeNotFoundException(Integer id){
        super("could not find programme with id : "+id);
    }
}
