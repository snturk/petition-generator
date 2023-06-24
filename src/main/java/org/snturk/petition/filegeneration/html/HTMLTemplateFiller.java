package org.snturk.petition.filegeneration.html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.feedback.FeedbackModel;
import org.snturk.petition.model.Issuer;
import org.snturk.petition.signature.SignatureContext;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Fills the given HTML string with the given PetitionModel
 */
public class HTMLTemplateFiller {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTMLTemplateFiller.class);

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
        if (html == null || model == null) {
            LOGGER.error("HTML or model cannot be null");
            throw new IllegalArgumentException("HTML or model cannot be null");
        }

        if (html.contains("{{name}}")) {
            html = html.replace("{{name}}", model.getName());
        } else {
            LOGGER.warn("HTML does not contain {{name}}");
        }

        if (html.contains("{{title}}")) {
            html = html.replace("{{title}}", model.getTitle());
        } else {
            LOGGER.warn("HTML does not contain {{title}}");
        }

        if (html.contains("{{issuedDate}}")) {
            html = html.replace("{{issuedDate}}", model.getIssuedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } else {
            LOGGER.warn("HTML does not contain {{issuedDate}}");
        }

        if (html.contains("{{id}}")) {
            html = html.replace("{{id}}", model.getId());
        } else {
            LOGGER.warn("HTML does not contain {{id}}");
        }

        if (html.contains("{{content}}")) {
            html = html.replace("{{content}}", model.getContent());
        } else {
            LOGGER.warn("HTML does not contain {{content}}");
        }

        // For each issuer, we need to add a signature to list of signatures
        if (model.getSignatures() != null && !model.getSignatures().isEmpty()) {
            html = html.replace(" not-signed", " signed");
            html = html.replace("{{signatures}}", prepareSignatureList(model));
        } else {
            LOGGER.info("Petition is not signed, no signatures found");
        }

        if (model.getFeedbacks() != null && !model.getFeedbacks().isEmpty()) {
            html = html.replace(" no-feedback", " has-feedback");
            html = html.replace("{{feedbacks}}", prepareFeedbackList(model));
        } else {
            LOGGER.info("Petition has no feedback, no feedbacks found");
        }

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

    /**
     * Returns the signature of the given issuer
     * @param model PetitionModel model
     * @param issuer Issuer
     * @return SignatureContext of the given issuer
     */
    private static SignatureContext getSignatureOfIssuer(PetitionModel model, Issuer issuer) {
        for (SignatureContext signatureContext : model.getSignatures()) {
            if (signatureContext.issuer().equals(issuer)) {
                return signatureContext;
            }
        }
        return null;
    }

    private static String prepareFeedbackList(PetitionModel model) {
        StringBuilder sb = new StringBuilder();
        List<FeedbackModel> feedbackModels = model.getFeedbacks();

        for (FeedbackModel feedbackModel : feedbackModels) {
            sb.append("<div class=\"feedback\">");
            sb.append("<li>").append(feedbackModel.getContent()).append("</li>");
            sb.append("<li>").append("Issued at ").append(feedbackModel.getIssuedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("</li>");
            sb.append("<li>").append(feedbackModel.getIssuer().getCompleteName()).append("</li>");

            if (feedbackModel.getSignatureContext() != null) {
                for (SignatureContext signatureContext : feedbackModel.getSignatureContext()) {
                    sb.append("<li class=\"signature\">").append(signatureContext.signatureInfo().getSignatureType().getTemplateString(model, signatureContext)).append("</li>");
                }
            }
            sb.append("</div>");
        }

        return sb.toString();
    }
}
