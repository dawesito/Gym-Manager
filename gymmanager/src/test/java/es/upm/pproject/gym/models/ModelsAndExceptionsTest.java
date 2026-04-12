package es.upm.pproject.gym.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import es.upm.pproject.gym.services.exceptions.*;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;

class ModelsAndExceptionsTest {

    @Test
    void testGymClassModel() {
        GymClass gymClass = new GymClass("Yoga", "Ana");
        assertEquals("Yoga", gymClass.name());
        assertEquals("Ana", gymClass.trainer());
    }

    @Test
    void testPersonModel() {
        Person person = new Person(1, "John", "john@ex.com");
        assertEquals(1, person.id());
        assertEquals("John", person.name());
        assertEquals("john@ex.com", person.mailAdress());
    }

    @Test
    void testExceptions() {
        assertNotNull(new ClassNotFoundException("msg"));
        assertNotNull(new EnrollmentNotFoundException("msg"));
        assertNotNull(new FullClassException("msg"));
        assertNotNull(new MemberNotFoundException("msg"));
        assertNotNull(new PrimaryKeyDuplication("msg"));
    }
}
