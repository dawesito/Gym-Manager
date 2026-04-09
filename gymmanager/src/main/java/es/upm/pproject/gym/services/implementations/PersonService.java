package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IPersonInfrastructure;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.interfaces.IPersonService;

class PersonService implements IPersonService {

    IPersonInfrastructure infra;

    public PersonService(IPersonInfrastructure infra){
        this.infra = infra;
    }

    @Override
    public void registerPerson(Integer id, String name, String mailAdress) throws NullPointerException, IllegalArgumentException {

        if(id == null || name == null || mailAdress == null){
                    throw new NullPointerException("Arguments can't be null");
                }

        if(!mailAdress.contains("@") || mailAdress.endsWith(".")){
            throw new IllegalArgumentException("Email format is invalid");
        }

        infra.registerPerson(id, name, mailAdress);
    }

    public Person[] getAllUsers(){

        return infra.getAllUsers();
    }
}
