package es.upm.pproject.gym.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.upm.pproject.gym.infrastructure.implementations.InfrastructureFactory;
import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.interfaces.IEnrollService;
import es.upm.pproject.gym.services.interfaces.IGymClassService;
import es.upm.pproject.gym.services.interfaces.IPersonService;

import java.lang.reflect.Constructor;

class ExtendedServiceTest {

    private IPersonService personService;
    private IGymClassService gymClassService;
    private IEnrollService enrollService;

    @BeforeEach
    void setUp() {
        personService = ServiceFactory.getIPersonService(InfrastructureFactory.getIPersonInfrastructure());
        gymClassService = ServiceFactory.getIGymClassService(InfrastructureFactory.getIGymClassInfrastructure());
        enrollService = ServiceFactory.getIEnrollService(InfrastructureFactory.getIEnrollInfrastructure());
        InfrastructureFactory.getIPersonInfrastructure().reset();
        InfrastructureFactory.getIGymClassInfrastructure().reset();
        InfrastructureFactory.getIEnrollInfrastructure().reset();
    }

    @Test
    void testPersonServiceBranches() throws Exception {
        // Blank name
        assertThrows(IllegalArgumentException.class, () -> personService.registerPerson(1, "   ", "john@ex.com"));
        // Blank email
        assertThrows(IllegalArgumentException.class, () -> personService.registerPerson(1, "John", "   "));
        // Email without @
        assertThrows(IllegalArgumentException.class, () -> personService.registerPerson(1, "John", "john-ex.com"));
        // Email ending in .
        assertThrows(IllegalArgumentException.class, () -> personService.registerPerson(1, "John", "john@ex."));
        
        // Sorting users
        personService.registerPerson(1, "Z", "z@ex.com");
        personService.registerPerson(2, "A", "a@ex.com");
        Person[] all = personService.getAllUsers();
        assertEquals("a@ex.com", all[0].mailAdress());
        assertEquals("z@ex.com", all[1].mailAdress());
    }

    @Test
    void testGymClassServiceBranches() throws Exception {
        // Blank name
        assertThrows(IllegalArgumentException.class, () -> gymClassService.registerClass("   ", "Ana"));
        // Blank trainer
        assertThrows(IllegalArgumentException.class, () -> gymClassService.registerClass("Yoga", "   "));
        
        // Restart null
        assertThrows(NullPointerException.class, () -> gymClassService.restartClass(null));
        
        // Sorting classes
        gymClassService.registerClass("Z", "Ana");
        gymClassService.registerClass("A", "Ana");
        GymClass[] all = gymClassService.getAllClasses();
        assertEquals("A", all[0].name());
        assertEquals("Z", all[1].name());
    }

    @Test
    void testEnrollServiceBranches() throws Exception {
        personService.registerPerson(1, "John", "john@ex.com");
        gymClassService.registerClass("Yoga", "Ana");
        
        // Null args
        assertThrows(NullPointerException.class, () -> enrollService.enroll(null, "Yoga"));
        assertThrows(NullPointerException.class, () -> enrollService.enroll("john@ex.com", null));
        assertThrows(NullPointerException.class, () -> enrollService.cancelEnrollment(null, "Yoga"));
        assertThrows(NullPointerException.class, () -> enrollService.cancelEnrollment("john@ex.com", null));
        assertThrows(NullPointerException.class, () -> enrollService.getClassEnrolledPeople(null));
        
        // Sorting enrolled people
        personService.registerPerson(2, "Z", "z@ex.com");
        personService.registerPerson(3, "A", "a@ex.com");
        enrollService.enroll("z@ex.com", "Yoga");
        enrollService.enroll("a@ex.com", "Yoga");
        Person[] enrolled = enrollService.getClassEnrolledPeople("Yoga");
        assertEquals("A", enrolled[0].name());
        assertEquals("Z", enrolled[1].name());
    }

    @Test
    void testPrivateConstructors() throws Exception {
        // ServiceFactory
        Constructor<ServiceFactory> sfConstructor = ServiceFactory.class.getDeclaredConstructor();
        sfConstructor.setAccessible(true);
        assertNotNull(sfConstructor.newInstance());
        
        // InfrastructureFactory
        Constructor<InfrastructureFactory> ifConstructor = InfrastructureFactory.class.getDeclaredConstructor();
        ifConstructor.setAccessible(true);
        assertNotNull(ifConstructor.newInstance());
    }
}
