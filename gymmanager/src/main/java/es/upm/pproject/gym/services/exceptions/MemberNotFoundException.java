package es.upm.pproject.gym.services.exceptions;

public class MemberNotFoundException extends Exception {

    public MemberNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
