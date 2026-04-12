package es.upm.pproject.gym.infrastructure.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IEnrollInfrastructure;
import es.upm.pproject.gym.infrastructure.interfaces.IGymClassInfrastructure;
import es.upm.pproject.gym.infrastructure.interfaces.IPersonInfrastructure;

public class InfrastructureFactory {

    public static IEnrollInfrastructure getIEnrollInfrastructure(){
        return (IEnrollInfrastructure) new EnrollInfrastructure();
    }

    public static IGymClassInfrastructure getIGymClassInfrastructure(){
        return (IGymClassInfrastructure) new GymClassInfrastructure();
    }

    public static IPersonInfrastructure getIPersonInfrastructure(){
        return (IPersonInfrastructure) new PersonInfrastructure();
    }

}
