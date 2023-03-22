# Petition Generator

This is a simple library to store (and generate, in future) petitions.

## Installation

TODO: Describe the installation process

## Usage

Basically, a petition needs to be created, and then it can be signed by people.
A petition can be owned by a `Person`, or by an `Organization`. All these classes implements `Issuer` interface. An `Issuer` can be a `Person` or an `Organization`.
If yu have a different application that needs another type of `Issuer`, you can implement the `Issuer` interface.  

> *Tip*: `Issuer` interface has a single method, `getComplateName()` that returns the complete name of the issuer. For `Person` it returns the name and surname (and middle name if exists), and for `Organization` it returns the name of the organization and type (Shelby Ltd.).

## Features to implement

- [ ] Generate petition in PDF format #5
- [ ] Generate petition in HTML format #6
- [ ] Petition needs to hold signatures #7
- [ ] Petition needs to hold a list of signers with their Issuers #8
- [ ] Petitions can have a field named `feedbacks` that holds a list of feedbacks with feedback owners #9
- [ ] (Not Sure) A petition can have `linkedPetitions` or `referencedPetitions` to other petitions

## Contributing

1. Fork it
2. Create your feature branch: `git checkout -b feature|fix/ISSUE_NUMBER/DESCRIPTION` (ex. `git checkout -b feature/5/generate-petition-in-pdf-format`)
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin feature|fix/ISSUE_NUMBER/DESCRIPTION` (ex. `git push origin feature/5/generate-petition-in-pdf-format`)
5. Submit a pull request 🚀
6. If you want to contribute to the project, please, create an issue first, and then create a branch with the issue number and a description of the issue. (ex. `feature/5/generate-petition-in-pdf-format`)