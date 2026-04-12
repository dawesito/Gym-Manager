package es.upm.pproject.gym.services.interfaces;

import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.EnrollmentNotFoundException;
import es.upm.pproject.gym.services.exceptions.FullClassException;
import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

public interface IEnrollService {

    public void enroll(String mailAdress, String name) throws MemberNotFoundException, ClassNotFoundException, FullClassException, PrimaryKeyDuplication, NullPointerException; // Enrolls an user in a class, both must exist and the class must have less than 20 users already enrolled.

    public void cancelEnrollment(String mailAdress, String name) throws EnrollmentNotFoundException, MemberNotFoundException, ClassNotFoundException, NullPointerException; // Cancels the enrollment of an user in a class.

    /*
        ¿Debería añadir opciones de llamar a esta función con los objetos en vez de con los identificadores?
    */

    public Person[] getClassEnrolledPeople(String name) throws ClassNotFoundException, NullPointerException; // Returns an array of enrolled people from a class.
}
