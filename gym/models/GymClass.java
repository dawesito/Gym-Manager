package gym.models;

public class GymClass{
    private String name;
    private String trainer;

    public GymClass(String name, String trainer){
        this.name = name;
        this.trainer = trainer;
    }

    public String name(){
        return name;
    }

    public String trainer(){
        return trainer;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTrainer(String trainer){
        this.trainer = trainer;
    }
}