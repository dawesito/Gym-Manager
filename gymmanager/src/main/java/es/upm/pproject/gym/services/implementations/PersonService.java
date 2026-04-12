package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IPersonInfrastructure;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;
import es.upm.pproject.gym.services.interfaces.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;

class PersonService implements IPersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    IPersonInfrastructure infra;

    public PersonService(IPersonInfrastructure infra){
        this.infra = infra;
    }

    @Override
    public void registerPerson(Integer id, String name, String mailAdress) throws NullPointerException, PrimaryKeyDuplication, IllegalArgumentException {

        if(id == null || name == null || mailAdress == null){
            logger.error("Attempted to register person with null arguments: id={}, name={}, mailAdress={}", id, name, mailAdress);
            throw new NullPointerException("Arguments can't be null");
        }

        if(name.trim().isEmpty() || mailAdress.trim().isEmpty()){
            logger.error("Attempted to register person with blank arguments: name='{}', mailAdress='{}'", name, mailAdress);
            throw new IllegalArgumentException("Name and email cannot be blank");
        }

        if(!mailAdress.contains("@") || mailAdress.endsWith(".")){
            logger.error("Attempted to register person with invalid email format: {}", mailAdress);
            throw new IllegalArgumentException("Email format is invalid");
        }

        logger.debug("Registering person {} with id {} and email {}", name, id, mailAdress);
        infra.registerPerson(id, name, mailAdress);
    }

    @Override
    public Person[] getAllUsers(){
        logger.debug("Retrieving and sorting all users by email");
        Person[] users = infra.getAllUsers();
        Arrays.sort(users, Comparator.comparing(Person::mailAdress));
        return users;
    }
}
