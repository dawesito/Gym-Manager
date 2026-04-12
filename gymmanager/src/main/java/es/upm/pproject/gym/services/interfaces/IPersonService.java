package es.upm.pproject.gym.services.interfaces;

import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

public interface IPersonService {

    /**
     * Registers a new person in the gym.
     * @param id The identification number (cannot be null).
     * @param name The name of the person (cannot be null or blank).
     * @param email The email address (cannot be null or blank, must be valid format).
     * @throws PrimaryKeyDuplication if a person with the same email already exists.
     * @throws NullPointerException if any argument is null.
     * @throws IllegalArgumentException if name or email is blank, or email format is invalid.
     */
    public void registerPerson(Integer id, String name, String mailAdress) throws NullPointerException, PrimaryKeyDuplication, IllegalArgumentException;

    /**
     * Returns a list of all registered people in the system, sorted by email.
     * @return Array of all registered Person objects.
     */
    public Person[] getAllUsers(); // Returns a list of all registered users sorted by email.

}
