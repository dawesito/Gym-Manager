package es.upm.pproject.gym.services.interfaces;

import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

public interface IGymClassService {

    /**
     * Registers a new class in the system.
     * @param name The name of the class (cannot be null or blank).
     * @param trainer The name of the trainer (cannot be null or blank).
     * @throws PrimaryKeyDuplication if a class with the same name already exists.
     * @throws NullPointerException if name or trainer is null.
     * @throws IllegalArgumentException if name or trainer is blank.
     */
    public void registerClass(String name, String trainer) throws PrimaryKeyDuplication, NullPointerException, IllegalArgumentException;

    /**
     * Returns a list of all registered classes, sorted by name.
     * @return Array of all registered GymClass objects.
     */
    public GymClass[] getAllClasses();
    
    /**
     * Restarts a class by removing all enrolled people.
     * @param name The name of the class.
     * @throws ClassNotFoundException if the class name is not registered.
     * @throws NullPointerException if name is null.
     */
    public void restartClass(String name) throws ClassNotFoundException, NullPointerException;

}
