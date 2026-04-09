package es.upm.pproject.gym.infrastructure.interfaces;

import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;

public interface IGymClassInfrastructure {

    public void registerClass(String name, String trainer);

    public GymClass[] getAllClasses();

    public boolean isClassRegistered(String name);

    public void restartClass(String name) throws ClassNotFoundException, java.lang.ClassNotFoundException;

    /*
        ¿restartClass necesitaría throw ClassNotFoundException?
    */
}
