package es.upm.pproject.gym.infrastructure.interfaces;

import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;

import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;
import es.upm.pproject.gym.services.exceptions.EnrollmentNotFoundException;

public interface IEnrollInfrastructure {

    public void enroll(String mailAdress, String name) throws MemberNotFoundException, ClassNotFoundException, PrimaryKeyDuplication; // Enrolls an user in a class.

    public boolean isPersonEnrolled(String mailAdress, String name); // Returns true if the user is enrolled in the class.

    public void cancelEnrollment(String mailAdress, String name) throws MemberNotFoundException, ClassNotFoundException, EnrollmentNotFoundException; // Cancels the enrollment of an user in a class.

    public Person[] getClassEnrolledPeople(String name) throws ClassNotFoundException; // Returns an array of enrolled people from a class.

    public int getClassEnrolledAmmount(String name) throws ClassNotFoundException, NullPointerException; // Returns the ammount of enrolled peopole in a class

    public void reset(); // Clears all enrollments.
}
