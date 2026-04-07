package es.upm.pproject.gym.infrastructure.interfaces;

import es.upm.pproject.gym.models.GymClass;

public interface IGymClassInfrastructure {

    public void registerClass(String name, String trainer); // Registers class in the system.

    public GymClass[] getAllClasses(); // Returns a list of all registered gym classes sorted by name.

    public boolean isClassRegistered(String name); // Returns true if a class with that name already exists.

    public void restartClass(String name); // Removes all enrolled people in the class

    /*
        ¿restartClass necesitaría throw ClassNotFoundException?
    */
}
