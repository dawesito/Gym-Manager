package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IEnrollInfrastructure;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.EnrollmentNotFoundException;
import es.upm.pproject.gym.services.exceptions.FullClassException;
import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;
import es.upm.pproject.gym.services.interfaces.IEnrollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;

public class EnrollService implements IEnrollService {

    private static final Logger logger = LoggerFactory.getLogger(EnrollService.class);
    private IEnrollInfrastructure infra;

    public EnrollService(IEnrollInfrastructure infra) {
        this.infra = infra;
    }

    @Override
    public void enroll(String mailAdress, String name)
            throws MemberNotFoundException, ClassNotFoundException, FullClassException, PrimaryKeyDuplication,
            NullPointerException {

        if (mailAdress == null || name == null) {
            logger.error("Attempted to enroll with null arguments: mailAdress={}, name={}", mailAdress, name);
            throw new NullPointerException("Arguments name and mailAdress can't be null");
        }

        if (infra.getClassEnrolledAmmount(name) >= 20) {
            logger.warn("Class {} is full (20/20). Cannot enroll {}", name, mailAdress);
            throw new FullClassException("Class " + name + " is full");
        }

        logger.debug("Executing enrollment for {} in {}", mailAdress, name);
        infra.enroll(mailAdress, name);
    }

    @Override
    public void cancelEnrollment(String mailAdress, String name)
            throws EnrollmentNotFoundException, MemberNotFoundException, ClassNotFoundException, NullPointerException {
        if (mailAdress == null || name == null) {
            logger.error("Attempted to cancel enrollment with null arguments: mailAdress={}, name={}", mailAdress, name);
            throw new NullPointerException("Arguments name and mailAdress can't be null");
        }

        logger.debug("Cancelling enrollment for {} in {}", mailAdress, name);
        infra.cancelEnrollment(mailAdress, name);
    }

    @Override
    public Person[] getClassEnrolledPeople(String name) throws ClassNotFoundException, NullPointerException {
        if (name == null) {
            logger.error("Attempted to get enrolled people with null class name");
            throw new NullPointerException("Argument name can't be null");
        }

        logger.debug("Retrieving and sorting enrolled people for class {}", name);
        // Added to sort people by name before returning
        Person[] people = infra.getClassEnrolledPeople(name);
        Arrays.sort(people, Comparator.comparing(Person::name));
        return people;
    }
}
