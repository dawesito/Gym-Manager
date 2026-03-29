package gym.services.interfaces;

import gym.models.Person;
import gym.services.exceptions.ClassNotFoundException;
import gym.services.exceptions.EnrollmentNotFoundException;
import gym.services.exceptions.MemberNotFoundException;

public interface IEnrollService {

    public void enroll(String mailAdress, String name) throws MemberNotFoundException, ClassNotFoundException; // Enrolls an user in a class, both must exist and the class must have less than 20 users already enrolled.

    public void cancelEnrollment(int id, String name) throws EnrollmentNotFoundException, MemberNotFoundException; // Cancels the enrollment of an user in a class.

    /*
        ¿Debería añadir opciones de llamar a esta función con los objetos en vez de con los identificadores?
    */

    public Person[] getClassEnrolledPeople(String name) throws ClassNotFoundException; //Returns an array of enrolled people from a class

}