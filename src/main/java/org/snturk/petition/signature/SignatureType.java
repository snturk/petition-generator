package org.snturk.petition.signature;

import org.snturk.petition.PetitionModel;


/**
 * SignatureType is an interface that defines how a signature is applied to a petition.
 */
public interface SignatureType {

    /**
     * Name of the signature type can take values like E-Signature, Handwritten, etc.
     */
    String getName();

    /**
     * String that applied to the petition template on generation stage.
     * Result of this method will be replaced with ${signature} placeholder in the template.
     * FIXME: This wont work for all signature types. Maybe some image based signatures will be added in the future.
     */
    String getTemplateString(PetitionModel petitionModel, SignatureContext context);
}
