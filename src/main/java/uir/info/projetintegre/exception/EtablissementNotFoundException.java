package uir.info.projetintegre.exception;

public class EtablissementNotFoundException extends RuntimeException{

    public EtablissementNotFoundException(Integer id){
        super("could not find etablissement with id : "+id);
    }
}
