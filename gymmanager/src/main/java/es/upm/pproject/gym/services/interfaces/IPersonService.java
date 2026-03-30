package es.upm.pproject.gym.services.interfaces;

import es.upm.pproject.gym.models.Person;

public interface IPersonService {

    public void registerPerson(int id, String name, String mailAdress) throws NullPointerException, IllegalArgumentException; // Registers a person into the system, throws NullPointerException if any of the arguments is null and throws IllegalArgumentException if the mail adress isn't correctly formated 

    /*
        ¿Se pueden repetir email? en caso de que no habría que comprobar en general clave primaria no repetida
    */

    public Person[] getAllUsers(); // Returns a list of all registered users sorted by name.

}
