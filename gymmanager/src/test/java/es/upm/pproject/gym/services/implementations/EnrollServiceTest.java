package es.upm.pproject.gym.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.upm.pproject.gym.infrastructure.implementations.EnrollInfrastructure;
import es.upm.pproject.gym.infrastructure.implementations.GymClassInfrastructure;
import es.upm.pproject.gym.infrastructure.implementations.PersonInfrastructure;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;

public class EnrollServiceTest {

    private EnrollService enrollService;
    private PersonService personService;
    private GymClassService gymClassService;

    @BeforeEach
    void setUp() {
        enrollService = new EnrollService(new EnrollInfrastructure());
        personService = new PersonService(new PersonInfrastructure());
        gymClassService = new GymClassService(new GymClassInfrastructure());
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
    void testEnroll_MemberNotFound() {
        gymClassService.registerClass("Spinning", "Marcos");
        assertThrows(MemberNotFoundException.class, () -> enrollService.enroll("unknown@example.com", "Spinning"));
    }

    @Test
    void testEnroll_ClassNotFound() {
        personService.registerPerson(10, "Carlos", "carlos@example.com");
        assertThrows(ClassNotFoundException.class, () -> enrollService.enroll("carlos@example.com", "UnknownClass"));
    }
}
