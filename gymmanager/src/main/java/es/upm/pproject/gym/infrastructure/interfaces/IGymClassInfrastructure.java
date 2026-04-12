package es.upm.pproject.gym.infrastructure.interfaces;

import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

public interface IGymClassInfrastructure {

    public void registerClass(String name, String trainer) throws PrimaryKeyDuplication;

    public GymClass[] getAllClasses();

    public boolean isClassRegistered(String name);

    public void restartClass(String name) throws ClassNotFoundException;

    public void reset(); // Clears all registered classes.
}
