package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IPersonInfrastructure;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;
import es.upm.pproject.gym.services.interfaces.IPersonService;

import java.util.Arrays;
import java.util.Comparator;

class PersonService implements IPersonService {

    IPersonInfrastructure infra;

    public PersonService(IPersonInfrastructure infra){
        this.infra = infra;
    }

    @Override
    public void registerPerson(Integer id, String name, String mailAdress) throws NullPointerException, PrimaryKeyDuplication, IllegalArgumentException {

        if(id == null || name == null || mailAdress == null){
            throw new NullPointerException("Arguments can't be null");
        }

        if(name.trim().isEmpty() || mailAdress.trim().isEmpty()){
            throw new IllegalArgumentException("Name and email cannot be blank");
        }

        if(!mailAdress.contains("@") || mailAdress.endsWith(".")){
            throw new IllegalArgumentException("Email format is invalid");
        }

        infra.registerPerson(id, name, mailAdress);
    }

    @Override
    public Person[] getAllUsers(){
        Person[] users = infra.getAllUsers();
        Arrays.sort(users, Comparator.comparing(Person::mailAdress));
        return users;
    }
}
