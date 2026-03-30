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
}