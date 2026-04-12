package es.upm.pproject.gym.infrastructure.implementations;

import es.upm.pproject.gym.infrastructure.interfaces.IEnrollInfrastructure;
import es.upm.pproject.gym.models.Person;
import es.upm.pproject.gym.services.exceptions.ClassNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import es.upm.pproject.gym.services.exceptions.MemberNotFoundException;
import es.upm.pproject.gym.services.exceptions.PrimaryKeyDuplication;
import es.upm.pproject.gym.services.exceptions.EnrollmentNotFoundException;

class EnrollInfrastructure implements IEnrollInfrastructure {

    private static Map<String, List<String>> enrollments = new HashMap<>();
    private static final String FILE_NAME = "enrollments.csv";

    static {
        load();
    }

    private static void load() {

        List<String[]> data = PersistenceManager.readCSV(FILE_NAME);

        for (String[] row : data) {

            if (row.length >= 2) {

                List<String> rowList = Arrays.asList(row);

                String className = rowList.get(0);
                List<String> mails = new ArrayList<>(rowList.subList(1, rowList.size()));

                enrollments.put(className, mails);
            }
        }
    }

    private static void save() {
        List<String[]> data = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : enrollments.entrySet()) {
            List<String> row = new ArrayList<>();
            row.add(entry.getKey());
            row.addAll(entry.getValue());
            data.add(row.toArray(new String[0]));
        }
        PersistenceManager.writeCSV(FILE_NAME, data);
    }

    @Override
    public void enroll(String mailAdress, String name) throws MemberNotFoundException, ClassNotFoundException, PrimaryKeyDuplication {
        if (!PersonInfrastructure.isPersonRegisteredStatic(mailAdress)) {
            throw new MemberNotFoundException("Member with mail " + mailAdress + " not found");
        }
        if (!GymClassInfrastructure.classExists(name)) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }
        if(isPersonEnrolled(mailAdress, name)) {
            throw new PrimaryKeyDuplication("Member with mail " + mailAdress + " is already enrolled in class " + name);
        }
        enrollments.computeIfAbsent(name, k -> new ArrayList<>()).add(mailAdress);
        save();
    }

    @Override
    public void reset() {
        enrollments.clear();
        save();
    }

    @Override
    public boolean isPersonEnrolled(String mailAdress, String name) {
        List<String> enrolled = enrollments.get(name);
        return enrolled != null && enrolled.contains(mailAdress);
    }

    @Override
    public void cancelEnrollment(String mailAdress, String name)
            throws MemberNotFoundException, ClassNotFoundException, EnrollmentNotFoundException {
        if (!PersonInfrastructure.isPersonRegisteredStatic(mailAdress)) {
            throw new MemberNotFoundException("Member with mail " + mailAdress + " not found");
        }
        if (!GymClassInfrastructure.classExists(name)) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }
        List<String> enrolled = enrollments.get(name);
        if (enrolled == null || !enrolled.contains(mailAdress)) {
            throw new EnrollmentNotFoundException("Member " + mailAdress + " is not enrolled in class " + name);
        }
        enrolled.remove(mailAdress);
        save();
    }

    @Override
    public Person[] getClassEnrolledPeople(String name) throws ClassNotFoundException {
        if (!GymClassInfrastructure.classExists(name)) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }
        List<String> enrolledMails = enrollments.get(name);
        if (enrolledMails == null) {
            return new Person[0];
        }

        List<Person> people = new ArrayList<>();
        for (String mail : enrolledMails) {
            Person p = PersonInfrastructure.getPersonByMail(mail);
            if (p != null) {
                people.add(p);
            }
        }
        return people.toArray(new Person[0]);
    }

    @Override
    public int getClassEnrolledAmmount(String name) throws ClassNotFoundException {
        if (!GymClassInfrastructure.classExists(name)) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }
        List<String> enrolled = enrollments.get(name);
        return enrolled != null ? enrolled.size() : 0;
    }

    public static void clearEnrollmentsForClass(String name) {
        enrollments.remove(name);
        save();
    }
}
