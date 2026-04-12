package es.upm.pproject.gym.infrastructure.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IPersonInfrastructure;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PersonInfrastructure implements IPersonInfrastructure {

    private static Map<String, Person> users = new HashMap<>();
    private static final String FILE_NAME = "users.csv";

    static {
        load();
    }

    private static void load() {
        List<String[]> data = PersistenceManager.readCSV(FILE_NAME);
        for (String[] row : data) {
            if (row.length == 3) {
                int id = Integer.parseInt(row[0]);
                String name = row[1];
                String mail = row[2];
                users.put(mail, new Person(id, name, mail));
            }
        }
    }

    private static void save() {
        List<String[]> data = new ArrayList<>();
        for (Person p : users.values()) {
            data.add(new String[] { String.valueOf(p.id()), p.name(), p.mailAdress() });
        }
        PersistenceManager.writeCSV(FILE_NAME, data);
    }

    @Override
    public void registerPerson(int id, String name, String mailAdress) throws PrimaryKeyDuplication{
        if(isPersonRegistered(mailAdress)){
            throw new PrimaryKeyDuplication("User with email " + mailAdress + " is already registered");
        }

        users.put(mailAdress, new Person(id, name, mailAdress));
        save();
    }

    @Override
    public void reset() {
        users.clear();
        save();
    }

    @Override
    public Person[] getAllUsers() {
        return users.values().toArray(new Person[0]);
    }

    @Override
    public boolean isPersonRegistered(String mail) {
        return isPersonRegisteredStatic(mail);
    }

    public static boolean isPersonRegisteredStatic(String mail) {
        return users.containsKey(mail);
    }

    public static Person getPersonByMail(String mail) {
        return users.get(mail);
    }
}
