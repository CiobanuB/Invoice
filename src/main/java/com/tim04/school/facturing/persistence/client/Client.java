package com.tim04.school.facturing.persistence.client;

import javax.persistence.*;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue
    private Long clientID;
    @Column(name = "userID")
    private Long userID;
    @Column(name = "name")
    private String name;
    @Column(name = "regDate")
    private String regDate;
    @Column(name = "mail")
    private String mail;
    @Column(name = "cif")
    private Integer cif;
    @Column(name = "adress")
    private String adress;
    @Column(name = "contactPerson")
    private String contactPerson;

   public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getCif() {
        return cif;
    }

    public void setCif(Integer cif) {
        this.cif = cif;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientID=" + clientID +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", regDate='" + regDate + '\'' +
                ", mail='" + mail + '\'' +
                ", cif=" + cif +
                ", adress='" + adress + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }
}
