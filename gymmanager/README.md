# Gym Manager Project

This project implements a gym management system to handle classes, people, and enrollments.

## Mandatory Interface: IGymManager

The `IGymManager` interface defines all the methods required to interact with the system. It is located at `es.upm.pproject.gym.services.interfaces.IGymManager`.

### Functional Description

The interface provides the following capabilities:

1.  **Register a Class**: `registerClass(String name, String trainer)`
    *   Registers a new class with its name and trainer.
    *   Checks that name and trainer are not null or blank.
    *   Throws `PrimaryKeyDuplication` if the class name already exists.

2.  **Register a Person**: `registerPerson(Integer id, String name, String email)`
    *   Registers a new person with their ID, name, and email.
    *   Checks that ID, name, and email are not null or blank.
    *   Validates that the email format is correct (contains '@' and does not end with '.').
    *   Throws `PrimaryKeyDuplication` if the email already exists.

3.  **Enroll in a Class**: `enroll(String email, String className)`
    *   Enrolls a person in a class using their email and the class name.
    *   Checks that the person and class are already registered.
    *   Ensures the class has at most 20 people.
    *   Throws appropriate exceptions (`MemberNotFoundException`, `ClassNotFoundException`, `FullClassException`) if conditions are not met.

4.  **List Enrolled People**: `getClassEnrolledPeople(String className)`
    *   Returns a list of people enrolled in a specific class.
    *   The list includes identification number, name, and email.
    *   The list is alphabetically sorted by name.

5.  **Cancel Enrollment**: `cancelEnrollment(String email, String className)`
    *   Removes a person from a class.
    *   Checks that the person is registered and enrolled in the class.
    *   Throws `EnrollmentNotFoundException` if the enrollment does not exist.

6.  **Restart Class**: `restartClass(String className)`
    *   Removes all people currently enrolled in the specified class.

7.  **List All Registered People**: `getAllUsers()`
    *   Returns a list of all people registered in the system with all their information.
    *   The list is sorted by email.

8.  **List All Registered Classes**: `getAllClasses()`
    *   Returns a list of all registered classes (name and trainer).
    *   The list is sorted by name.

9.  **Exception Handling**:
    *   The system throws specific exceptions when pre-conditions are not met, ensuring robust error handling.
