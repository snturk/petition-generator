package org.snturk.petition.filegeneration.html;

import org.snturk.petition.PetitionModel;
import org.snturk.petition.model.Issuer;

/**
 * Fills the given HTML string with the given PetitionModel
 */
public class HTMLTemplateFiller {

    private HTMLTemplateFiller() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Fills the given HTML string with the given PetitionModel
     * @param html HTML string
     * @param model PetitionModel model
     * @return Filled HTML string
     */
    public static String fillTemplate(String html, PetitionModel model) {
        html = html.replace("{{name}}", model.getName());
        html = html.replace("{{title}}", model.getTitle());
        html = html.replace("{{issuedDate}}", model.getIssuedDate().toString());
        html = html.replace("{{id}}", model.getId());
        html = html.replace("{{content}}", model.getContent());

        // For each issuer, we need to add a signature to list of signatures
        if (model.getSignatures() != null && !model.getSignatures().isEmpty()) {
            html = html.replace(" not-signed", " signed");
        }
        html = html.replace("{{signatures}}", prepareSignatureList(model));

        return html;
    }

    /**
     * Prepares the signature list as <li>ISSUER_NAME</li>
     * @param model PetitionModel model
     * @return Prepared signature list
     */
    private static String prepareSignatureList(PetitionModel model) {
        StringBuilder sb = new StringBuilder();

        Issuer[] signers = model.getSigners();
        if (signers != null) {
            for (Issuer signer : signers) {
                sb.append("<li>").append(signer.getCompleteName()).append("</li>");
            }
        }

        return sb.toString();
    }
}
