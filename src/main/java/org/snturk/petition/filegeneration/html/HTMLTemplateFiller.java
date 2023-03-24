package org.snturk.petition.filegeneration.html;

import org.snturk.petition.PetitionModel;

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

        // TODO: Signatures of the issuers must be defined with order
        // TODO: Issuers can be multiple, so we need to define a list of issuers
        html = html.replace("{{issuer}}", model.getIssuer().getCompleteName());

        return html;
    }
}
