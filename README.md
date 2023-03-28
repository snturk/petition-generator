# 📄 Petition Generator

This is a simple library to store (and generate, in future) petitions.

## 📥 Installation

TODO: Describe the installation process

## Usage

Basically, a petition needs to be created, and then it can be signed by people.
A petition can be owned by a `Person`, or by an `Organization`. All these classes implements `Issuer` interface. An `Issuer` can be a `Person` or an `Organization`.
If you have a different application that needs another type of `Issuer`, you can implement the `Issuer` interface.  

> *TIP*: `Issuer` interface has a single method, `getComplateName()` that returns the complete name of the issuer. For `Person` it returns the name and surname (and middle name if exists), and for `Organization` it returns the name of the organization and type (Shelby Ltd.).

### Petition Model

petition-generator use `org.immutables` to generate models. By using that, your petitions is immutable, and you can't change it after it's created. If you want to change a petition, you need to create a new one.

Immutables library generates an implementation of `PetitionModel` interface. You can use that interface to create a new petition, or to get the values of the petition.

```java
public class MyPetitionApp {
    
    PetitionModel petition = ImmutablePetitionModel.builder()
            .title("Petition Title")
            .description("Petition Description")
            .owner(Person.builder()
                    .name("John")
                    .surname("Doe")
                    .build())
            // More attributes can be added here
            .build();
}
```

### Sign a petition

A petition can be signed by multiple `Issuers` (which can be `Person` or `Organization`). To sign a petition, you need to use `SignatureService` class.

When a petition signed, because of PetitionModel's instances are immutable the service layer will re-create a copy of the petition with the signatures applied.

> *NOTE:* Since `PetitionModel` is an immutable class, a sign cannot be removed. If you want to remove a signature, you need to create a new petition.

```java
public class MyPetitionApp {
    
    PetitionModel petition = ImmutablePetitionModel.builder()
            .title("Petition Title")
            .description("Petition Description")
            .owner(Person.builder()
                    .name("John")
                    .surname("Doe")
                    .build())
            // More attributes can be added here
            .build();
    
    SignatureService signatureService = new SignatureService();
    
    // Sign the petition
    // The service layer will re-create a copy of the petition with the signatures applied and return it
    PetitionModel signedPetition = signatureService.performSign(petition, Person.builder()
            .name("Jane")
            .surname("Doe")
            .build());
}
```

### Generating a petition

#### HTML

`HTMLFileGenerationService` is a service that generates a petition in HTML format. It uses `org.apache.commons.io.FileUtils` to generate the HTML file.

A template file provided by default named `petitionTemplate.html` is used to generate the petition. You can change the template file by defining a property named `petition-generator.file-generation.html,template-path` in your `application.properties` file.

```java
public class MyPetitionApp {
    
    PetitionModel petition = ImmutablePetitionModel.builder()
            .title("Petition Title")
            .description("Petition Description")
            .owner(Person.builder()
                    .name("John")
                    .surname("Doe")
                    .build())
            // More attributes can be added here
            .build();
    
    HTMLFileGenerationService htmlFileGenerationService = new HTMLFileGenerationService();
    
    // Generate the petition in HTML format
    Path path  = Paths.get("path/to/the/file.html");
    htmlFileGenerationService.generateFile(signedPetition, path);
}
```

#### PDF

TODO: Write usage instructions after implementing PDF generation

#### DOCX

TODO: Write usage instructions after implementing DOCX generation

## 📝 Features to implement

- [ ] Generate petition in PDF format Issue #5
- [X] Generate petition in HTML format Issue #6
- [X] Petition needs to hold signatures Issue #7
- [X] Petition needs to hold a list of signers with their Issuers Issue #8
- [ ] Petitions can have a field named `feedbacks` that holds a list of feedbacks with feedback owners Issue #9
- [ ] (Not Sure) A petition can have `linkedPetitions` or `referencedPetitions` to other petitions

## 🤝 Contributing

1. Fork it
2. Create your feature branch: `git checkout -b feature|fix|docs/ISSUE_NUMBER/DESCRIPTION` (ex. `git checkout -b feature/5/generate-petition-in-pdf-format`)
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin feature|fix/ISSUE_NUMBER/DESCRIPTION` (ex. `git push origin feature/5/generate-petition-in-pdf-format`)
5. Submit a pull request 🚀
6. If you want to contribute to the project, please, create an issue first, and then create a branch with the issue number and a description of the issue. (ex. `feature/5/generate-petition-in-pdf-format`)
