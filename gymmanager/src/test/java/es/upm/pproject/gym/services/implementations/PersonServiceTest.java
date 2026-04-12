package es.upm.pproject.gym.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.pproject.gym.infrastructure.implementations.InfrastructureFactory;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.interfaces.IPersonService;

class PersonServiceTest {

    private IPersonService personService;

    @BeforeEach
    void setUp() {
        personService = ServiceFactory.getIPersonService(InfrastructureFactory.getIPersonInfrastructure());
        InfrastructureFactory.getIPersonInfrastructure().reset(); // Clean state
    }

    @Test
    void testRegisterPerson_Success() {
        assertDoesNotThrow(() -> personService.registerPerson(1, "John Doe", "john@example.com"));
        Person[] users = personService.getAllUsers();
        boolean found = false;
        for (Person p : users) {
            if (p.mailAdress().equals("john@example.com")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testRegisterPerson_InvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> personService.registerPerson(2, "Jane Doe", "jane-example.com"));
    }

    @Test
    void testRegisterPerson_NullArgs() {
        assertThrows(NullPointerException.class, () -> personService.registerPerson(null, "Jane Doe", "jane@example.com"));
    }
}
