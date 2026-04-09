package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IEnrollInfrastructure;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.EnrollmentNotFoundException;
import es.upm.pproject.gym.services.exceptions.FullClassException;
import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.interfaces.IEnrollService;

public class EnrollService implements IEnrollService {

    private IEnrollInfrastructure infra;

    public EnrollService(IEnrollInfrastructure infra) {
        this.infra = infra;
    }

    public void enroll(String mailAdress, String name)
            throws MemberNotFoundException, ClassNotFoundException, FullClassException, NullPointerException {

        if (mailAdress == null || name == null) {
            throw new NullPointerException("Arguments name and mailAdress can't be null");
        }

        try {
            if (infra.getClassEnrolledAmmount(name) >= 20) {
                throw new FullClassException("Class " + name + " is full");
            }
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class " + name + " isn't in the system");
        }

        infra.enroll(mailAdress, name); // Falta tratar MemberNotFoundException y ClassNotFoundException
    }

    public void cancelEnrollment(String mailAdress, String name)
            throws EnrollmentNotFoundException, MemberNotFoundException, NullPointerException {
        if (mailAdress == null || name == null) {
            throw new NullPointerException("Arguments name and mailAdress can't be null");
        }

        //
        // Lo he tocado para poder manejar los errores.
        //
        try {
            infra.cancelEnrollment(mailAdress, name);
        } catch (MemberNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (EnrollmentNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //
        //
        //

        // Falta tratar MemberNotFoundException y ClassNotFoundException
    }

    public Person[] getClassEnrolledPeople(String name) throws ClassNotFoundException, NullPointerException {
        if (name == null) {
            throw new NullPointerException("Argument name can't be null");
        }

        return infra.getClassEnrolledPeople(name); // Falta tratar ClassNotFoundException
    }
}
