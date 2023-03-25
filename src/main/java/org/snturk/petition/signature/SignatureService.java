package org.snturk.petition.signature;

import org.snturk.petition.ImmutablePetitionModel;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.model.Issuer;

import java.time.LocalDateTime;

/**
 * A service that handles the signature operations of a petition.
 */
public class SignatureService {

    public PetitionModel performSign(PetitionModel petitionModel, Issuer... signers) {
        LocalDateTime signatureDate = LocalDateTime.now();
        SignatureInfo signatureInfo = new SignatureInfo(signers, signatureDate);

        petitionModel.checkValidity(signatureInfo);
        return ImmutablePetitionModel.builder()
                .from(petitionModel)
                .addSignatures(signatureInfo)
                .build();
    }
}
