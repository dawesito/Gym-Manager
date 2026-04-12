package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IGymClassInfrastructure;
import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.services.interfaces.IGymClassService;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

import java.util.Arrays;
import java.util.Comparator;

class GymClassService implements IGymClassService {

    private IGymClassInfrastructure infra;

    public GymClassService(IGymClassInfrastructure infra) {
        this.infra = infra;
    }

    @Override
    public void registerClass(String name, String trainer)
            throws NullPointerException, PrimaryKeyDuplication, IllegalArgumentException {
        if (name == null || trainer == null) {
            throw new NullPointerException("Arguments name and trainer can't be null");
        }

        if (name.trim().isEmpty() || trainer.trim().isEmpty()) {
            throw new IllegalArgumentException("Name and trainer cannot be blank");
        }

        infra.registerClass(name, trainer);
    }

    // Added to sort classes by name before returning
    @Override
    public GymClass[] getAllClasses() {
        GymClass[] classes = infra.getAllClasses();
        Arrays.sort(classes, Comparator.comparing(GymClass::name));
        return classes;
    }

    @Override
    public void restartClass(String name) throws ClassNotFoundException, NullPointerException {

        if (name == null) {
            throw new NullPointerException("Argument name can't be null");
        }

        infra.restartClass(name);
    }
}
