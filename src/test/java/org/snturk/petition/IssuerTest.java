package org.snturk.petition;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.snturk.petition.exceptions.InvalidIssuerException;
import org.snturk.petition.model.Issuer;
import org.snturk.petition.model.Organization;
import org.snturk.petition.model.Person;

import static org.junit.jupiter.api.Assertions.*;

public class IssuerTest {


    @Test @DisplayName("Should create person") void shouldCreatePerson() {
        Issuer issuerPerson = new Person("Muratcan", "Şentürk");
        assertEquals("Muratcan Şentürk", issuerPerson.getCompleteName());

        Issuer issuerPerson2 = new Person("Muratcan", "Example", "Şentürk");
        assertEquals("Muratcan Example Şentürk", issuerPerson2.getCompleteName());

        assertThrows(InvalidIssuerException.class, () -> new Person(null, "Şentürk"));
        assertThrows(InvalidIssuerException.class, () -> new Person("Muratcan", null));
        assertThrows(InvalidIssuerException.class, () -> new Person("Muratcan", "Example", null));
        assertThrows(InvalidIssuerException.class, () -> new Person("Muratcan", null, "Şentürk"));
        assertThrows(InvalidIssuerException.class, () -> new Person(null, "Example", "Şentürk"));
        assertThrows(InvalidIssuerException.class, () -> new Person("", "Şentürk"));
        assertThrows(InvalidIssuerException.class, () -> new Person("Muratcan", ""));
        assertThrows(InvalidIssuerException.class, () -> new Person("Muratcan", "Example", ""));
        assertThrows(InvalidIssuerException.class, () -> new Person("Muratcan", "", "Şentürk"));
        assertThrows(InvalidIssuerException.class, () -> new Person("", "Example", "Şentürk"));
    }


    @Test @DisplayName("Should create organization") void shouldCreateOrganization() {
        Issuer issuerOrganization = new Organization("Example");
        assertEquals("Example", issuerOrganization.getCompleteName());

        Issuer issuerOrganization2 = new Organization("Example", "Ltd.");
        assertEquals("Example (Ltd.)", issuerOrganization2.getCompleteName());

        assertThrows(InvalidIssuerException.class, () -> new Organization(null));
        assertThrows(InvalidIssuerException.class, () -> new Organization(""));
        assertThrows(InvalidIssuerException.class, () -> new Organization("Example", null));
        assertThrows(InvalidIssuerException.class, () -> new Organization("Example", ""));
    }


    @Test @DisplayName("Should create issuer") void shouldCreateIssuer() {
        Issuer issuerPerson = new Person("Muratcan", "Şentürk");
        assertEquals("Muratcan Şentürk", issuerPerson.getCompleteName());

        Issuer issuerOrganization = new Organization("Example");
        assertEquals("Example", issuerOrganization.getCompleteName());
    }
}
