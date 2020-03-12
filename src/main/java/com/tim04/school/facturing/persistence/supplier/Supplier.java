package com.tim04.school.facturing.persistence.supplier;

import javax.persistence.*;

@Entity
@Table(name = "Supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long supplierID;
    @Column(name = "name")
    private String name;
    @Column(name = "regDate")
    private String regDate;
    @Column(name = "mail")
    private String mail;
    @Column(name = "cifSupplier")
    private Integer cifSupplier;
    @Column(name = "Adress")
    private String adress;
    @Column(name = "bankAccount")
    private String bankAccount;
    @Column(name = "website")
    private String website;
    @Column(name = "userMail")
    private String userMail;

    public Long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Long supplierID) {
        this.supplierID = supplierID;
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

    public Integer getCifSupplier() {
        return cifSupplier;
    }

    public void setCifSupplier(Integer cifSupplier) {
        this.cifSupplier = cifSupplier;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierID=" + supplierID +
                ", name='" + name + '\'' +
                ", regDate='" + regDate + '\'' +
                ", mail='" + mail + '\'' +
                ", cifSupplier=" + cifSupplier +
                ", adress='" + adress + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
