package org.snturk.petition;

import jakarta.annotation.Nullable;
import org.immutables.value.Value;
import org.snturk.petition.enums.PetitionType;
import org.snturk.petition.model.Feedback;
import org.snturk.petition.model.Issuer;
import org.snturk.petition.signature.SignatureContext;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * A model that holds all core features of a petition. Also, it is used to generate petition files.
 */
@Value.Immutable
public interface PetitionModel {

    static ImmutablePetitionModel.Builder builder() {
        return ImmutablePetitionModel.builder();
    }

    /**
     * By default id is generated by idGenerator function, but it can be overridden by setting id explicitly.
     * @return
     */
    @Value.Default
    default @Nullable String getId() {
        return getIdGenerator().apply(this);
    }

    /**
     * Name of the petition
     * @return
     */
    String getName();

    /**
     * Title of the petition
     * @return
     */
    String getTitle();

    /**
     * Petitioner(s) of the petition
     */
    List<Issuer> getPetitioners();

    /**
     * Content of the petition
     * @return String
     */
    String getContent();

    /**
     * Date of the petition
     * @return LocalDateTime
     */
    @Value.Default
    default LocalDateTime getIssuedDate() {
        return LocalDateTime.now();
    }

    /**
     * Type of the petition
     * @see PetitionType
     * @return PetitionType
     */
    @Nullable PetitionType getType();

    /**
     * Related attachments of the petition, key is the name of the attachment, value is the java.io.File object
     * @return Initial attachments of the petition
     */
    @Nullable Set<File> getAttachments();

    /**
     * Function to generate id of the petition
     * @return Function that takes a PetitionModel and returns a String as id
     */
    Function<PetitionModel, String> getIdGenerator();

    /**
     * Unmodifiable list of the petition steps
     */
    List<Feedback> getFeedbacks();

    /**
     * Signature context of the petition, hold details of the signature process that is applied to the petition when it is signed by the petitioners.
     * Not all petitions require signature, so this field can be null. Also, Feedbacks holds their own signature context.
     */
    @Nullable Set<SignatureContext> getSignatures();

}
