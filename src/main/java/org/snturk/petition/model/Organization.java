package org.snturk.petition.model;

public class Organization implements Issuer {

    private String corporateName;
    private String corporationType;

    public Organization(String corporateName) {
        this.corporateName = corporateName;
    }

    public Organization(String corporateName, String corporationType) {
        this.corporateName = corporateName;
        this.corporationType = corporationType;
    }

    @Override
    public String getCompleteName() {
        return getCorporateName() + (getCorporationType().isEmpty() ? "" : " (" + getCorporationType() + ")");
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
