package org.snturk.petition.model;

import org.snturk.petition.exceptions.InvalidIssuerException;
import org.snturk.petition.utils.StringUtils;

/**
 * Organization is a type of issuer that is a legal entity.
 */
public class Organization implements Issuer {

    private String organizationName;
    private String organizationType;

    /**
     * Constructor of Organization
     * @param organizationName
     */
    public Organization(String organizationName) {
        if (StringUtils.isNullOrEmpty(organizationName)) {
            throw new InvalidIssuerException("Organization name cannot be null or empty");
        }
        this.organizationName = organizationName;
    }

    /**
     * Constructor of Organization
     * @param organizationName
     * @param organizationType
     */
    public Organization(String organizationName, String organizationType) {
        if (StringUtils.isNullOrEmpty(organizationName)) {
            throw new InvalidIssuerException("Organization name cannot be null or empty");
        }
        if (StringUtils.isNullOrEmpty(organizationType)) {
            throw new InvalidIssuerException("Organization type cannot be null or empty, if there is no corporation type, use the other constructor");
        }
        this.organizationName = organizationName;
        this.organizationType = organizationType;
    }

    @Override
    public String getCompleteName() {
        String type = StringUtils.isNullOrEmpty(getOrganizationType()) ? "" : " (" + getOrganizationType() + ")";
        return getOrganizationName() + type;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }
}
