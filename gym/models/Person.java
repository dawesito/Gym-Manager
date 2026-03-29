package gym.models;

public class Person {
    private int id;
    private String name;
    private String mailAdress;

    public Person(int id, String name, String mailAdress){
        this.id = id;
        this.name = name;
        this.mailAdress = mailAdress;
    }

    public int id(){
        return id;
    }

    public String name(){
        return name;
    }

    public String mailAdress(){
        return mailAdress;
    }
}
