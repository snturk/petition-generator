package org.snturk.petition.exceptions;

import org.snturk.petition.feedback.FeedbackModel;

/**
 * InvalidFeedbackException is thrown when a feedback is not valid.
 */
public class InvalidFeedbackException extends RuntimeException {

    public InvalidFeedbackException(String message) {
        super("Invalid feedback: " + message);
    }

    public InvalidFeedbackException(String message, FeedbackModel feedback) {
        super("Invalid feedback: " + message + ": " + feedback);
    }
}
