package es.upm.pproject.gym.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.upm.pproject.gym.infrastructure.implementations.InfrastructureFactory;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.interfaces.IEnrollService;
import es.upm.pproject.gym.services.interfaces.IGymClassService;
import es.upm.pproject.gym.services.interfaces.IPersonService;

public class EnrollServiceTest {

    private IEnrollService enrollService;
    private IPersonService personService;
    private IGymClassService gymClassService;

    @BeforeEach
    void setUp() {
        enrollService = ServiceFactory.getIEnrollService(InfrastructureFactory.getIEnrollInfrastructure());
        personService = ServiceFactory.getIPersonService(InfrastructureFactory.getIPersonInfrastructure());
        gymClassService = ServiceFactory.getIGymClassService(InfrastructureFactory.getIGymClassInfrastructure());
        InfrastructureFactory.getIPersonInfrastructure().reset();
        InfrastructureFactory.getIGymClassInfrastructure().reset();
        InfrastructureFactory.getIEnrollInfrastructure().reset();
    }

    @Test
    void testEnroll_Success() throws Exception {
        personService.registerPerson(10, "Carlos", "carlos@example.com");
        gymClassService.registerClass("Spinning", "Marcos");

        assertDoesNotThrow(() -> enrollService.enroll("carlos@example.com", "Spinning"));

        Person[] enrolled = enrollService.getClassEnrolledPeople("Spinning");
        assertEquals(1, enrolled.length);
        assertEquals("carlos@example.com", enrolled[0].mailAdress());
    }

    @Test
    void testEnroll_MemberNotFound() throws Exception{
        gymClassService.registerClass("Spinning", "Marcos");
        assertThrows(MemberNotFoundException.class, () -> enrollService.enroll("unknown@example.com", "Spinning"));
    }

    @Test
    void testEnroll_ClassNotFound() throws Exception{
        personService.registerPerson(10, "Carlos", "carlos@example.com");
        assertThrows(ClassNotFoundException.class, () -> enrollService.enroll("carlos@example.com", "UnknownClass"));
    }
}
