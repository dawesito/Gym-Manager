package es.upm.pproject.gym.services.exceptions;

public class PrimaryKeyDuplication extends Exception{

    public PrimaryKeyDuplication(String errorMessage){
        super(errorMessage);
    }
    
}
