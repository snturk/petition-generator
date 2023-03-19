package org.snturk.petition;

import org.immutables.value.Value;
import jakarta.annotation.Nullable;
import org.snturk.petition.enums.PetitionType;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

@Value.Immutable
@Value.Style(strictBuilder = true)
public abstract class Petition {

    @Value.Default
    public @Nullable String id() {
        return idGenerator().apply(this);
    }
    public abstract String name();
    public abstract String title();
    public abstract Issuer issuer();
    @Value.Default
    public LocalDate issuedDate() {
        return LocalDate.now();
    }
    public abstract @Nullable PetitionType type();
    public abstract @Nullable Map<String, File> attachments();
    public abstract Function<Petition, String> idGenerator();
}
