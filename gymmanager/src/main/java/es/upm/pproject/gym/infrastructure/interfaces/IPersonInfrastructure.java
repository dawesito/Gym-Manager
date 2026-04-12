package es.upm.pproject.gym.infrastructure.interfaces;

import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

public interface IPersonInfrastructure {

    public void registerPerson(int id, String name, String mailAdress) throws PrimaryKeyDuplication; // Registers a
                                                                                                     // person into the
                                                                                                     // system

    public Person[] getAllUsers(); // Returns a list of all registered users sorted by email.

    public boolean isPersonRegistered(String mail); // Returns true if a person with that mail adress already exists.

    public void reset(); // Clears all registered users.
}
