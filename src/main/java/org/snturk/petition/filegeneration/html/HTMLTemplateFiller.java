package org.snturk.petition.filegeneration.html;

import org.snturk.petition.PetitionModel;
import org.snturk.petition.model.Issuer;
import org.snturk.petition.signature.SignatureContext;

import java.util.List;

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
        if (model.getSignatures().size() > 0) {
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

        List<Issuer> petitioners = model.getPetitioners();
        for (Issuer issuer : petitioners) {
            sb.append("<div class=\"issuer-block\">");
            sb.append("<li>").append(issuer.getCompleteName()).append("</li>");
            SignatureContext signatureContext = getSignatureOfIssuer(model, issuer);
            if (signatureContext != null) {
                sb.append("<li class=\"signature\">").append(signatureContext.signatureInfo().getSignatureType().getTemplateString(model, signatureContext)).append("</li>");
            }
            sb.append("</div>");
        }

        return sb.toString();
    }

    private static SignatureContext getSignatureOfIssuer(PetitionModel model, Issuer issuer) {
        for (SignatureContext signatureContext : model.getSignatures()) {
            if (signatureContext.issuer().equals(issuer)) {
                return signatureContext;
            }
        }
        return null;
    }
}
