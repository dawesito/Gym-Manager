package es.upm.pproject.gym.infrastructure.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IEnrollInfrastructure;
import es.upm.pproject.gym.infrastructure.interfaces.IGymClassInfrastructure;
import es.upm.pproject.gym.infrastructure.interfaces.IPersonInfrastructure;

public class InfrastructureFactory {

    private InfrastructureFactory() {
        // Evita instanciación
    }

    public static IEnrollInfrastructure getIEnrollInfrastructure(){
        return new EnrollInfrastructure();
    }

    public static IGymClassInfrastructure getIGymClassInfrastructure(){
        return new GymClassInfrastructure();
    }

    public static IPersonInfrastructure getIPersonInfrastructure(){
        return new PersonInfrastructure();
    }

}
