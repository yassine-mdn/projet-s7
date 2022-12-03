package uir.info.projetintegre.exception;

public class NiveauEtudeNotFoundException extends RuntimeException{

    public NiveauEtudeNotFoundException (Integer id){
        super("could not find niveau_etude with id : "+id);
    }
}
