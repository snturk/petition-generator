package org.snturk.petition.model;

import jakarta.annotation.Nullable;
import org.immutables.value.Value;

/**
 * Step definiton to hold petition steps. Each step defines where the petition is addressed to and what isser is
 * responsible for the step.
 */
@Value.Immutable
public interface Step {

    static ImmutableStep.Builder builder() {
        return ImmutableStep.builder();
    }

    /**
     * Address (or direction) of the step
     * @return
     */
    String getAddress();

    /**
     * Issuer of the step, can be determined later or can be set explicitly
     * @return issuer of the step
     */
    @Nullable
    Issuer getIssuer();
}
