package es.upm.pproject.gym.services.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IEnrollInfrastructure;
import es.upm.pproject.gym.infrastructure.interfaces.IGymClassInfrastructure;
import es.upm.pproject.gym.infrastructure.interfaces.IPersonInfrastructure;
import es.upm.pproject.gym.services.interfaces.IEnrollService;
import es.upm.pproject.gym.services.interfaces.IGymClassService;
import es.upm.pproject.gym.services.interfaces.IGymManager;
import es.upm.pproject.gym.services.interfaces.IPersonService;

public class ServiceFactory {
    
    public static IEnrollService getIEnrollService(IEnrollInfrastructure infra){
        return (IEnrollService) new EnrollService(infra);
    }

    public static IGymClassService getIGymClassService(IGymClassInfrastructure infra){
        return (IGymClassService) new GymClassService(infra);
    }

    public static IPersonService getIPersonService(IPersonInfrastructure infra){
        return (IPersonService) new PersonService(infra);
    }

    public static IGymManager getGymManager(IPersonService personService, IGymClassService classService, IEnrollService enrollService) {
        return new GymManager(personService, classService, enrollService);
    }
}
