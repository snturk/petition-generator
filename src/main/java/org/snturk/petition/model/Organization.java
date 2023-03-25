package org.snturk.petition.model;

import org.snturk.petition.exceptions.InvalidIssuerException;
import org.snturk.petition.utils.StringUtils;

public class Organization implements Issuer {

    private String corporateName;
    private String corporationType;

    public Organization(String corporateName) {
        if (StringUtils.isNullOrEmpty(corporateName)) {
            throw new InvalidIssuerException("Corporate name cannot be null or empty");
        }
        this.corporateName = corporateName;
    }

    public Organization(String corporateName, String corporationType) {
        if (StringUtils.isNullOrEmpty(corporateName)) {
            throw new InvalidIssuerException("Corporate name cannot be null or empty");
        }
        if (StringUtils.isNullOrEmpty(corporationType)) {
            throw new InvalidIssuerException("Corporation type cannot be null or empty, if there is no corporation type, use the other constructor");
        }
        this.corporateName = corporateName;
        this.corporationType = corporationType;
    }

    @Override
    public String getCompleteName() {
        String type = StringUtils.isNullOrEmpty(getCorporationType()) ? "" : " (" + getCorporationType() + ")";
        return getCorporateName() + type;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCorporationType() {
        return corporationType;
    }

    public void setCorporationType(String corporationType) {
        this.corporationType = corporationType;
    }
}
