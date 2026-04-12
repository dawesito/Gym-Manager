package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.EnrollmentNotFoundException;
import es.upm.pproject.gym.services.exceptions.FullClassException;
import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;
import es.upm.pproject.gym.services.interfaces.IEnrollService;
import es.upm.pproject.gym.services.interfaces.IGymClassService;
import es.upm.pproject.gym.services.interfaces.IGymManager;
import es.upm.pproject.gym.services.interfaces.IPersonService;

public class GymManager implements IGymManager {

    private final IPersonService personService;
    private final IGymClassService classService;
    private final IEnrollService enrollService;

    public GymManager(IPersonService personService, IGymClassService classService, IEnrollService enrollService) {
        this.personService = personService;
        this.classService = classService;
        this.enrollService = enrollService;
    }

    @Override
    public void registerClass(String name, String trainer) throws PrimaryKeyDuplication, NullPointerException, IllegalArgumentException {
        classService.registerClass(name, trainer);
    }

    @Override
    public void registerPerson(Integer id, String name, String email) throws PrimaryKeyDuplication, NullPointerException, IllegalArgumentException {
        personService.registerPerson(id, name, email);
    }

    @Override
    public void enroll(String email, String className) throws MemberNotFoundException, ClassNotFoundException, FullClassException, PrimaryKeyDuplication, NullPointerException {
        enrollService.enroll(email, className);
    }

    @Override
    public Person[] getClassEnrolledPeople(String className) throws ClassNotFoundException, NullPointerException {
        return enrollService.getClassEnrolledPeople(className);
    }

    @Override
    public void cancelEnrollment(String email, String className) throws EnrollmentNotFoundException, MemberNotFoundException, ClassNotFoundException, NullPointerException {
        enrollService.cancelEnrollment(email, className);
    }

    @Override
    public void restartClass(String className) throws ClassNotFoundException, NullPointerException {
        classService.restartClass(className);
    }

    @Override
    public Person[] getAllUsers() {
        return personService.getAllUsers();
    }

    @Override
    public GymClass[] getAllClasses() {
        return classService.getAllClasses();
    }

    @Override
    public void reset() {
        // We can't directly access infra from services if they are not exposed.
        // But for testing purposes, we can either add reset to services or use another way.
        // Let's assume we can get them from the factory or just use the static nature (though not ideal).
        // A better way is to add reset to the service interfaces.
        // However, since we want to be practical:
        es.upm.pproject.gym.infrastructure.implementations.InfrastructureFactory.getIPersonInfrastructure().reset();
        es.upm.pproject.gym.infrastructure.implementations.InfrastructureFactory.getIGymClassInfrastructure().reset();
        es.upm.pproject.gym.infrastructure.implementations.InfrastructureFactory.getIEnrollInfrastructure().reset();
    }
}
