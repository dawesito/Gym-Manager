package es.upm.pproject.gym.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.pproject.gym.infrastructure.implementations.InfrastructureFactory;
import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.*;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.interfaces.IGymManager;

public class GymManagerTest {

    private IGymManager gymManager;

    @BeforeEach
    void setUp() {
        gymManager = ServiceFactory.getGymManager(
                ServiceFactory.getIPersonService(InfrastructureFactory.getIPersonInfrastructure()),
                ServiceFactory.getIGymClassService(InfrastructureFactory.getIGymClassInfrastructure()),
                ServiceFactory.getIEnrollService(InfrastructureFactory.getIEnrollInfrastructure()));
        gymManager.reset(); // Ensure a clean state for each test
    }

    // 1. A new class can be registered. Checks for null and blank.
    @Test
    void testRequirement1_RegisterClass() {
        assertDoesNotThrow(() -> gymManager.registerClass("Yoga", "Ana"));

        // Check duplicate
        assertThrows(PrimaryKeyDuplication.class, () -> gymManager.registerClass("Yoga", "Other"));

        // Check null
        assertThrows(NullPointerException.class, () -> gymManager.registerClass(null, "Ana"));
        assertThrows(NullPointerException.class, () -> gymManager.registerClass("Yoga", null));

        // Check blank
        assertThrows(IllegalArgumentException.class, () -> gymManager.registerClass("", "Ana"));
        assertThrows(IllegalArgumentException.class, () -> gymManager.registerClass("Yoga", "   "));
    }

    // 2. A new person can be registered. Checks for null, blank, and email format.
    @Test
    void testRequirement2_RegisterPerson() {
        assertDoesNotThrow(() -> gymManager.registerPerson(1, "John Doe", "john@example.com"));

        // Check duplicate email
        assertThrows(PrimaryKeyDuplication.class, () -> gymManager.registerPerson(2, "Jane Doe", "john@example.com"));

        // Check null
        assertThrows(NullPointerException.class, () -> gymManager.registerPerson(null, "John", "john@ex.com"));
        assertThrows(NullPointerException.class, () -> gymManager.registerPerson(1, null, "john@ex.com"));

        // Check blank
        assertThrows(IllegalArgumentException.class, () -> gymManager.registerPerson(1, "", "john@ex.com"));
        assertThrows(IllegalArgumentException.class, () -> gymManager.registerPerson(1, "John", "   "));

        // Check email format (must contain '@' and not end with '.')
        assertThrows(IllegalArgumentException.class, () -> gymManager.registerPerson(3, "John", "johnexample.com"));
        assertThrows(IllegalArgumentException.class, () -> gymManager.registerPerson(3, "John", "john@example."));
    }

    // 3. A person can be enrolled in a class. Checks for registration, class
    // existence, and 20 people limit.
    @Test
    void testRequirement3_Enroll() throws Exception {
        gymManager.registerPerson(1, "John", "john@ex.com");
        gymManager.registerClass("Yoga", "Ana");

        assertDoesNotThrow(() -> gymManager.enroll("john@ex.com", "Yoga"));

        // Person must be registered
        assertThrows(MemberNotFoundException.class, () -> gymManager.enroll("unknown@ex.com", "Yoga"));

        // Class must be registered
        assertThrows(ClassNotFoundException.class, () -> gymManager.enroll("john@ex.com", "UnknownClass"));

        // Check 20 people limit
        gymManager.registerClass("FullClass", "Trainer");
        for (int i = 0; i < 20; i++) {
            String email = "p" + i + "@ex.com";
            gymManager.registerPerson(100 + i, "Person " + i, email);
            gymManager.enroll(email, "FullClass");
        }

        gymManager.registerPerson(200, "Extra", "extra@ex.com");
        assertThrows(FullClassException.class, () -> gymManager.enroll("extra@ex.com", "FullClass"));
    }

    // 4. Given a class name, returns enrolled people list (ID, name, email) sorted
    // by name.
    @Test
    void testRequirement4_ListEnrolledPeopleSorted() throws Exception {
        gymManager.registerClass("Yoga", "Ana");
        gymManager.registerPerson(1, "Zoe", "zoe@ex.com");
        gymManager.registerPerson(2, "Alice", "alice@ex.com");
        gymManager.registerPerson(3, "Bob", "bob@ex.com");

        gymManager.enroll("zoe@ex.com", "Yoga");
        gymManager.enroll("alice@ex.com", "Yoga");
        gymManager.enroll("bob@ex.com", "Yoga");

        Person[] enrolled = gymManager.getClassEnrolledPeople("Yoga");
        assertEquals(3, enrolled.length);
        assertEquals("Alice", enrolled[0].name());
        assertEquals("Bob", enrolled[1].name());
        assertEquals("Zoe", enrolled[2].name());
    }

    // 5. Cancel enrollment. Checks for registration and enrollment.
    @Test
    void testRequirement5_CancelEnrollment() throws Exception {
        gymManager.registerPerson(1, "John", "john@ex.com");
        gymManager.registerClass("Yoga", "Ana");
        gymManager.enroll("john@ex.com", "Yoga");

        assertDoesNotThrow(() -> gymManager.cancelEnrollment("john@ex.com", "Yoga"));

        // Check not enrolled
        assertThrows(EnrollmentNotFoundException.class, () -> gymManager.cancelEnrollment("john@ex.com", "Yoga"));

        // Check unknown member
        assertThrows(MemberNotFoundException.class, () -> gymManager.cancelEnrollment("unknown@ex.com", "Yoga"));
    }

    // 6. Restart class removing all enrolled people.
    @Test
    void testRequirement6_RestartClass() throws Exception {
        gymManager.registerClass("Yoga", "Ana");
        gymManager.registerPerson(1, "John", "john@ex.com");
        gymManager.enroll("john@ex.com", "Yoga");

        assertEquals(1, gymManager.getClassEnrolledPeople("Yoga").length);

        gymManager.restartClass("Yoga");

        assertEquals(0, gymManager.getClassEnrolledPeople("Yoga").length);
    }

    // 7. List of all registered people sorted by email.
    @Test
    void testRequirement7_ListAllPeopleSortedByEmail() {
        assertDoesNotThrow(() -> gymManager.registerPerson(1, "Zoe", "zoe@ex.com"));
        assertDoesNotThrow(() -> gymManager.registerPerson(2, "Alice", "alice@ex.com"));
        assertDoesNotThrow(() -> gymManager.registerPerson(3, "Bob", "bob@ex.com"));

        Person[] all = gymManager.getAllUsers();
        assertEquals(3, all.length);
        assertEquals("alice@ex.com", all[0].mailAdress());
        assertEquals("bob@ex.com", all[1].mailAdress());
        assertEquals("zoe@ex.com", all[2].mailAdress());
    }

    // 8. List of all registered classes sorted by name.
    @Test
    void testRequirement8_ListAllClassesSortedByName() {
        assertDoesNotThrow(() -> gymManager.registerClass("Yoga", "Ana"));
        assertDoesNotThrow(() -> gymManager.registerClass("Zumba", "Bea"));
        assertDoesNotThrow(() -> gymManager.registerClass("Aerobics", "Cris"));

        GymClass[] all = gymManager.getAllClasses();
        assertEquals(3, all.length);
        assertEquals("Aerobics", all[0].name());
        assertEquals("Yoga", all[1].name());
        assertEquals("Zumba", all[2].name());
    }

    // 9. Exceptions thrown when pre-conditions do not hold (already tested in
    // previous requirements).
    @Test
    void testRequirement9_ExceptionRobustness() {
        // This is covered by negative tests in other requirements, but let's add one
        // for null class name in restart
        assertThrows(NullPointerException.class, () -> gymManager.restartClass(null));
        assertThrows(ClassNotFoundException.class, () -> gymManager.restartClass("Unknown"));
    }
}
