package com.swifton.swifton.Models;

public class DesignersProfile {

    private Integer id;
    private String username, designerid, firstname, lastname, email, dpassword, daddress, phoneno, dposition, deviceids, created_at, updated_at;

    public DesignersProfile(Integer id, String username, String designerid, String firstname, String lastname, String email, String dpassword, String daddress, String phoneno, String dposition, String deviceids, String created_at, String updated_at) {
        this.id = id;
        this.username = username;
        this.designerid = designerid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dpassword = dpassword;
        this.daddress = daddress;
        this.phoneno = phoneno;
        this.dposition = dposition;
        this.deviceids = deviceids;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getDesignerid() {
        return designerid;
    }

    public void setDesignerid(String designerid) {
        this.designerid = designerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDpassword() {
        return dpassword;
    }

    public void setDpassword(String dpassword) {
        this.dpassword = dpassword;
    }

    public String getDaddress() {
        return daddress;
    }

    public void setDaddress(String daddress) {
        this.daddress = daddress;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getDposition() {
        return dposition;
    }

    public void setDposition(String dposition) {
        this.dposition = dposition;
    }

    public String getDeviceids() {
        return deviceids;
    }

    public void setDeviceids(String deviceids) {
        this.deviceids = deviceids;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
