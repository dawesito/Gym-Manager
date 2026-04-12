package es.upm.pproject.gym.services.interfaces;

import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.EnrollmentNotFoundException;
import es.upm.pproject.gym.services.exceptions.FullClassException;
import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

/**
 * Interface defining all methods needed to interact with the Gym Manager
 * program.
 */
public interface IGymManager {

    /**
     * Registers a new class in the system.
     * 
     * @param name    The name of the class (cannot be null or blank).
     * @param trainer The name of the trainer (cannot be null or blank).
     * @throws PrimaryKeyDuplication    if a class with the same name already
     *                                  exists.
     * @throws NullPointerException     if name or trainer is null.
     * @throws IllegalArgumentException if name or trainer is blank.
     */
    void registerClass(String name, String trainer)
            throws PrimaryKeyDuplication, NullPointerException, IllegalArgumentException;

    /**
     * Registers a new person in the gym.
     * 
     * @param id    The identification number (cannot be null).
     * @param name  The name of the person (cannot be null or blank).
     * @param email The email address (cannot be null or blank, must be valid
     *              format).
     * @throws PrimaryKeyDuplication    if a person with the same email already
     *                                  exists.
     * @throws NullPointerException     if any argument is null.
     * @throws IllegalArgumentException if name or email is blank, or email format
     *                                  is invalid.
     */
    void registerPerson(Integer id, String name, String email)
            throws PrimaryKeyDuplication, NullPointerException, IllegalArgumentException;

    /**
     * Enrolls a person in a class.
     * 
     * @param email     The email of the person.
     * @param className The name of the class.
     * @throws MemberNotFoundException if the person email is not registered.
     * @throws ClassNotFoundException  if the class name is not registered.
     * @throws FullClassException      if the class already has 20 people.
     * @throws PrimaryKeyDuplication   if the person is already enrolled in the
     *                                 class.
     * @throws NullPointerException    if any argument is null.
     */
    void enroll(String email, String className) throws MemberNotFoundException, ClassNotFoundException,
            FullClassException, PrimaryKeyDuplication, NullPointerException;

    /**
     * Returns the list of people enrolled in a class, sorted alphabetically by
     * name.
     * 
     * @param className The name of the class.
     * @return Array of Person objects enrolled in the class.
     * @throws ClassNotFoundException if the class name is not registered.
     * @throws NullPointerException   if className is null.
     */
    Person[] getClassEnrolledPeople(String className) throws ClassNotFoundException, NullPointerException;

    /**
     * Cancels a person's enrollment in a class.
     * 
     * @param email     The email of the person.
     * @param className The name of the class.
     * @throws EnrollmentNotFoundException if the person is not enrolled in the
     *                                     class.
     * @throws MemberNotFoundException     if the person email is not registered.
     * @throws ClassNotFoundException      if the class name is not registered.
     * @throws NullPointerException        if any argument is null.
     */
    void cancelEnrollment(String email, String className)
            throws EnrollmentNotFoundException, MemberNotFoundException, ClassNotFoundException, NullPointerException;

    /**
     * Restarts a class by removing all enrolled people.
     * 
     * @param className The name of the class.
     * @throws ClassNotFoundException if the class name is not registered.
     * @throws NullPointerException   if className is null.
     */
    void restartClass(String className) throws ClassNotFoundException, NullPointerException;

    /**
     * Returns a list of all people registered in the system, sorted by email.
     * 
     * @return Array of all registered Person objects.
     */
    Person[] getAllUsers();

    /**
     * Returns a list of all registered classes, sorted by name.
     * 
     * @return Array of all registered GymClass objects.
     */
    GymClass[] getAllClasses();

    /**
     * Resets the entire system, clearing all people, classes, and enrollments.
     * Useful for testing.
     */
    void reset();
}
