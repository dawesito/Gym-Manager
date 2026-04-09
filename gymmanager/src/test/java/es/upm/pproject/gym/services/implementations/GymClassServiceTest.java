package es.upm.pproject.gym.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.upm.pproject.gym.infrastructure.implementations.GymClassInfrastructure;
import es.upm.pproject.gym.models.GymClass;

public class GymClassServiceTest {

    private GymClassService gymClassService;

    @BeforeEach
    void setUp() {
        gymClassService = new GymClassService(new GymClassInfrastructure());
    }

    @Test
    void testRegisterClass_Success() {
        assertDoesNotThrow(() -> gymClassService.registerClass("Yoga", "Ana"));
        GymClass[] classes = gymClassService.getAllClasses();
        boolean found = false;
        for (GymClass c : classes) {
            if (c.name().equals("Yoga")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testRegisterClass_NullArgs() {
        assertThrows(NullPointerException.class, () -> gymClassService.registerClass(null, "Ana"));
        assertThrows(NullPointerException.class, () -> gymClassService.registerClass("Yoga", null));
    }
}
