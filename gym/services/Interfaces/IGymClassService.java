package gym.services.interfaces;

import gym.models.GymClass;
import gym.services.exceptions.ClassNotFoundException;

public interface IGymClassService {

    public void registerClass(String name, String trainer) throws NullPointerException; // Registers a new class in the system, throws NullPointerException if any of the arguments is null.

    public GymClass[] getAllClasses(); // Returns a list of all registered gym classes sorted by name.
    
    public void restartClass(String name) throws ClassNotFoundException; // Removes all enrolled people in the class

    /*
        Debería estar en IEnroll??
    */

    
}