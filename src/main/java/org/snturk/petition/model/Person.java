package org.snturk.petition.model;

import org.snturk.petition.exceptions.InvalidIssuerException;

public class Person implements Issuer {

    private String firstName;
    private String middleName;
    private String lastName;

    // TODO: Signature of the petition should be added here

    public Person(String firstName, String middleName, String lastName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new InvalidIssuerException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new InvalidIssuerException("Last name cannot be null or empty");
        }
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getCompleteName() {
        return getFirstName() + " " + (getMiddleName().isEmpty() ? "" : getMiddleName()) + " " + getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
