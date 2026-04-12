package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IGymClassInfrastructure;
import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.services.interfaces.IGymClassService;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;

class GymClassService implements IGymClassService {

    private static final Logger logger = LoggerFactory.getLogger(GymClassService.class);
    private IGymClassInfrastructure infra;

    public GymClassService(IGymClassInfrastructure infra) {
        this.infra = infra;
    }

    @Override
    public void registerClass(String name, String trainer)
            throws NullPointerException, PrimaryKeyDuplication, IllegalArgumentException {
        if (name == null || trainer == null) {
            logger.error("Attempted to register class with null arguments: name={}, trainer={}", name, trainer);
            throw new NullPointerException("Arguments name and trainer can't be null");
        }

        if (name.trim().isEmpty() || trainer.trim().isEmpty()) {
            logger.error("Attempted to register class with blank arguments: name='{}', trainer='{}'", name, trainer);
            throw new IllegalArgumentException("Name and trainer cannot be blank");
        }

        logger.debug("Registering class {} with trainer {}", name, trainer);
        infra.registerClass(name, trainer);
    }

    // Added to sort classes by name before returning
    @Override
    public GymClass[] getAllClasses() {
        logger.debug("Retrieving and sorting all classes");
        GymClass[] classes = infra.getAllClasses();
        Arrays.sort(classes, Comparator.comparing(GymClass::name));
        return classes;
    }

    @Override
    public void restartClass(String name) throws ClassNotFoundException, NullPointerException {

        if (name == null) {
            logger.error("Attempted to restart class with null name");
            throw new NullPointerException("Argument name can't be null");
        }

        logger.debug("Restarting class {}", name);
        infra.restartClass(name);
    }
}
