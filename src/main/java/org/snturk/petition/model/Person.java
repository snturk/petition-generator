package org.snturk.petition.model;

import com.google.common.base.Strings;
import org.snturk.petition.exceptions.InvalidIssuerException;

public class Person implements Issuer {

    private String firstName;
    private String middleName;
    private String lastName;

    // TODO: Signature of the petition should be added here

    public Person(String firstName, String middleName, String lastName) {
        if (Strings.isNullOrEmpty(firstName)) {
            throw new InvalidIssuerException("First name cannot be null or empty");
        }
        if (Strings.isNullOrEmpty(lastName)) {
            throw new InvalidIssuerException("Last name cannot be null or empty");
        }
        if (Strings.isNullOrEmpty(middleName)) {
            throw new InvalidIssuerException("Middle name cannot be null or empty, if there is no middle name, use the other constructor");
        }
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName) {
        if (Strings.isNullOrEmpty(firstName)) {
            throw new InvalidIssuerException("First name cannot be null or empty");
        }
        if (Strings.isNullOrEmpty(lastName)) {
            throw new InvalidIssuerException("Last name cannot be null or empty");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getCompleteName() {
        String middle = Strings.isNullOrEmpty(getMiddleName()) ? " " : " " + getMiddleName() + " ";
        return getFirstName() + middle + getLastName();
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
