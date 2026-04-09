package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IGymClassInfrastructure;
import es.upm.pproject.gym.models.GymClass;
import es.upm.pproject.gym.services.interfaces.IGymClassService;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;

class GymClassService implements IGymClassService {

    private IGymClassInfrastructure infra;

    public GymClassService(IGymClassInfrastructure infra) {
        this.infra = infra;
    }

    public void registerClass(String name, String trainer) throws NullPointerException {
        if (name == null || trainer == null) {
            throw new NullPointerException("Arguments name and trainer can't be null");
        }

        infra.registerClass(name, trainer);
    }

    public GymClass[] getAllClasses() {

        return infra.getAllClasses();
    }

    public void restartClass(String name) throws ClassNotFoundException, NullPointerException {
        // Quién tiene la responsabilidad de tirar ClassNotFound??

        if (name == null) {
            throw new NullPointerException("Argument name can't be null");
        }

        //
        // Meterle un arreglo para el throw de ClassNotFoundException
        //
        try {
            infra.restartClass(name);
        } catch (java.lang.ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //
        //
        //

    }
}
