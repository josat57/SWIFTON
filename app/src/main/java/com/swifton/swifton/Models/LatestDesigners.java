package com.swifton.swifton.Models;

public class LatestDesigners {
    private Integer id, approvalstatus, level;
    private String companyname, companyregno, companyaddress, country, zipcode,
            companystate, companycity,emailaddress, phone, website, companycode, verified, created_at, updated_at, logo;

    public LatestDesigners(Integer id, Integer approvalstatus, Integer level, String zipcode, String companyname,
                           String companyregno, String companyaddress, String country, String companystate,
                           String companycity, String emailaddress, String phone, String website, String companycode,
                           String verified, String created_at, String updated_at, String logo) {
        this.id = id;
        this.approvalstatus = approvalstatus;
        this.level = level;
        this.zipcode = zipcode;
        this.companyname = companyname;
        this.companyregno = companyregno;
        this.companyaddress = companyaddress;
        this.country = country;
        this.companystate = companystate;
        this.companycity = companycity;
        this.emailaddress = emailaddress;
        this.phone = phone;
        this.website = website;
        this.companycode = companycode;
        this.verified = verified;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.logo = logo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(Integer approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyregno() {
        return companyregno;
    }

    public void setCompanyregno(String companyregno) {
        this.companyregno = companyregno;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanystate() {
        return companystate;
    }

    public void setCompanystate(String companystate) {
        this.companystate = companystate;
    }

    public String getCompanycity() {
        return companycity;
    }

    public void setCompanycity(String companycity) {
        this.companycity = companycity;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at(){
        return updated_at;
    }

    public void setUpdated_at(String updated_at){
        this.updated_at = updated_at;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
