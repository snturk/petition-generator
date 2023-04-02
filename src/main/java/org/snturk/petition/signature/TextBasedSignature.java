package org.snturk.petition.signature;

import org.snturk.petition.PetitionModel;

import java.time.format.DateTimeFormatter;

public class TextBasedSignature implements SignatureType {

    @Override
    public String getName() {
        return "Text-based";
    }

    @Override
    public String getTemplateString(PetitionModel petitionModel, SignatureContext context) {
        return "Signed by " + context.issuer().getCompleteName() + " at " + context.signatureInfo().getSignedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
