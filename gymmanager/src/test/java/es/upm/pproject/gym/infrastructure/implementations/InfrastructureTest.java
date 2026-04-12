package es.upm.pproject.gym.infrastructure.implementations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.upm.pproject.gym.infrastructure.interfaces.IEnrollInfrastructure;
import es.upm.pproject.gym.infrastructure.interfaces.IGymClassInfrastructure;
import es.upm.pproject.gym.infrastructure.interfaces.IPersonInfrastructure;
import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.EnrollmentNotFoundException;
import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

import java.util.ArrayList;
import java.util.List;

class InfrastructureTest {

    private IPersonInfrastructure personInfra;
    private IGymClassInfrastructure classInfra;
    private IEnrollInfrastructure enrollInfra;

    @BeforeEach
    void setUp() {
        personInfra = InfrastructureFactory.getIPersonInfrastructure();
        classInfra = InfrastructureFactory.getIGymClassInfrastructure();
        enrollInfra = InfrastructureFactory.getIEnrollInfrastructure();
        personInfra.reset();
        classInfra.reset();
        enrollInfra.reset();
    }

    @Test
    void testPersonInfrastructure() throws Exception {
        personInfra.registerPerson(1, "John", "john@ex.com");
        assertTrue(personInfra.isPersonRegistered("john@ex.com"));
        assertNotNull(PersonInfrastructure.getPersonByMail("john@ex.com"));

        Person[] all = personInfra.getAllUsers();
        assertEquals(1, all.length);

        assertThrows(PrimaryKeyDuplication.class, () -> personInfra.registerPerson(2, "Jane", "john@ex.com"));
    }

    @Test
    void testGymClassInfrastructure() throws Exception {
        classInfra.registerClass("Yoga", "Ana");
        assertTrue(classInfra.isClassRegistered("Yoga"));

        GymClass[] all = classInfra.getAllClasses();
        assertEquals(1, all.length);

        assertThrows(PrimaryKeyDuplication.class, () -> classInfra.registerClass("Yoga", "Other"));

        assertDoesNotThrow(() -> classInfra.restartClass("Yoga"));
        assertThrows(ClassNotFoundException.class, () -> classInfra.restartClass("Unknown"));
    }

    @Test
    void testEnrollInfrastructure() throws Exception {
        personInfra.registerPerson(1, "John", "john@ex.com");
        classInfra.registerClass("Yoga", "Ana");

        enrollInfra.enroll("john@ex.com", "Yoga");
        assertTrue(enrollInfra.isPersonEnrolled("john@ex.com", "Yoga"));
        assertEquals(1, enrollInfra.getClassEnrolledAmmount("Yoga"));

        Person[] enrolled = enrollInfra.getClassEnrolledPeople("Yoga");
        assertEquals(1, enrolled.length);

        assertThrows(PrimaryKeyDuplication.class, () -> enrollInfra.enroll("john@ex.com", "Yoga"));
        assertThrows(MemberNotFoundException.class, () -> enrollInfra.enroll("unknown@ex.com", "Yoga"));
        assertThrows(ClassNotFoundException.class, () -> enrollInfra.enroll("john@ex.com", "Unknown"));

        assertDoesNotThrow(() -> enrollInfra.cancelEnrollment("john@ex.com", "Yoga"));
        assertThrows(EnrollmentNotFoundException.class, () -> enrollInfra.cancelEnrollment("john@ex.com", "Yoga"));

        assertThrows(ClassNotFoundException.class, () -> enrollInfra.getClassEnrolledPeople("Unknown"));
        assertThrows(ClassNotFoundException.class, () -> enrollInfra.getClassEnrolledAmmount("Unknown"));
    }

    @Test
    void testPersistenceManager() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "test1", "test2" });
        PersistenceManager.writeCSV("test.csv", data);

        List<String[]> read = PersistenceManager.readCSV("test.csv");
        assertEquals(1, read.size());
        assertEquals("test1", read.get(0)[0]);

        // Test non-existent file
        List<String[]> empty = PersistenceManager.readCSV("non-existent.csv");
        assertTrue(empty.isEmpty());
    }

    @Test
    void testInfrastructureLoadInvalidData() {
        // Create a file with invalid data (less than 2 columns)
        List<String[]> invalidData = new ArrayList<>();
        String[] row = { "only_one_column" };
        invalidData.add(row);

        assertDoesNotThrow(() -> PersistenceManager.writeCSV("invalid_enrollments.csv", invalidData));
        assertDoesNotThrow(() -> PersistenceManager.writeCSV("invalid_classes.csv", invalidData));
        assertDoesNotThrow(() -> PersistenceManager.writeCSV("invalid_users.csv", invalidData));

        // Triggering some operations that would load the data if they re-load
        // We just want to ensure it doesn't crash on invalid CSV data.
        List<String[]> read = PersistenceManager.readCSV("invalid_enrollments.csv");
        assertNotNull(read);
        assertEquals(1, read.size());
        assertEquals("only_one_column", read.get(0)[0]);
    }

    @Test
    void testInfrastructureFactory() {
        assertNotNull(InfrastructureFactory.getIPersonInfrastructure());
        assertNotNull(InfrastructureFactory.getIGymClassInfrastructure());
        assertNotNull(InfrastructureFactory.getIEnrollInfrastructure());
    }
}
