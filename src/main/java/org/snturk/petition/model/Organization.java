package org.snturk.petition.model;

import org.snturk.petition.exceptions.InvalidIssuerException;
import org.snturk.petition.utils.StringUtils;

public class Organization implements Issuer {

    private String organizationName;
    private String organizationType;

    public Organization(String organizationName) {
        if (StringUtils.isNullOrEmpty(organizationName)) {
            throw new InvalidIssuerException("Organization name cannot be null or empty");
        }
        this.organizationName = organizationName;
    }

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
