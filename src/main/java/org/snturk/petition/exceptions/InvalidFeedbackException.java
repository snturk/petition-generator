package org.snturk.petition.exceptions;

import org.snturk.petition.feedback.FeedbackModel;

/**
 * InvalidFeedbackException is thrown when a feedback is not valid.
 */
public class InvalidFeedbackException extends RuntimeException {

    /**
     * Constructor of InvalidFeedbackException
     * @param message Message of the exception
     */
    public InvalidFeedbackException(String message) {
        super("Invalid feedback: " + message);
    }

    /**
     * Constructor of InvalidFeedbackException
     * @param message Message of the exception
     * @param feedback Feedback that is not valid
     */
    public InvalidFeedbackException(String message, FeedbackModel feedback) {
        super("Invalid feedback: " + message + ": " + feedback);
    }
}
