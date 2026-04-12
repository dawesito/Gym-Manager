package es.upm.pproject.gym.infrastructure.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IGymClassInfrastructure;
import es.upm.pproject.gym.models.GymClass;

import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GymClassInfrastructure implements IGymClassInfrastructure {

    private static Map<String, GymClass> classes = new HashMap<>();
    private static final String FILE_NAME = "classes.csv";

    static {
        load();
    }

    private static void load() {
        List<String[]> data = PersistenceManager.readCSV(FILE_NAME);
        for (String[] row : data) {
            if (row.length == 2) {
                String name = row[0];
                String trainer = row[1];
                classes.put(name, new GymClass(name, trainer));
            }
        }
    }

    private static void save() {
        List<String[]> data = new ArrayList<>();
        for (GymClass c : classes.values()) {
            data.add(new String[] { c.name(), c.trainer() });
        }
        PersistenceManager.writeCSV(FILE_NAME, data);
    }

    @Override
    public void registerClass(String name, String trainer) throws PrimaryKeyDuplication {
        if (isClassRegistered(name)){
            throw new PrimaryKeyDuplication("Class by name " + name + " is already registered");
        }

        classes.put(name, new GymClass(name, trainer));
        save();
    }

    @Override
    public void reset() {
        classes.clear();
        save();
    }

    @Override
    public GymClass[] getAllClasses() {
        return classes.values().toArray(new GymClass[0]);
    }

    @Override
    public boolean isClassRegistered(String name) {
        return classes.containsKey(name);
    }

    @Override
    public void restartClass(String name) throws ClassNotFoundException {
        if (!isClassRegistered(name)) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }
        // When restarting the class, we clear the associated enrollments
        EnrollInfrastructure.clearEnrollmentsForClass(name);
    }

    public static boolean classExists(String name) {
        return classes.containsKey(name);
    }
}
