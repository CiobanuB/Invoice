package com.tim04.school.facturing.persistence.company;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
@Table(name = "Supplier")
public class Supplier {
    @Id
    @GeneratedValue
    private Long companyID;
    @Column(name = "userID")
    private Long userID;
    @Column(name = "name")
    private String name;
    @Column(name = "Rdate")
    private String rDate;
    @Column(name = "cuiFiscal")
    private int cuiFiscal;
    @Column(name = "J")
    private String J;
    @Column(name = "persoanaLegatura")
    private String persoanaLegatura;
    @Column(name = "mail")
    private String mail;
    @Column(name = "adress")
    private String adress;
    @Column(name = "bankAccount")
    private String bankAccount;

    public void setName(String name) {
        this.name = name;
    }

    public void setCuiFiscal(int cuiFiscal) {
        this.cuiFiscal = cuiFiscal;
    }

    public void setPersoanaLegatura(String persoanaLegatura) {
        this.persoanaLegatura = persoanaLegatura;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(Date date) {
        SimpleDateFormat dateForm = new SimpleDateFormat("dd/MM/Y");
        this.rDate = dateForm.format(date);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public String getName() {
        return name;
    }



    public int getCuiFiscal() {
        return cuiFiscal;
    }

    public void setcuiFiscal(int cuiFiscal) {
        this.cuiFiscal = cuiFiscal;
    }

    public String getJ() {
        return J;
    }

    public void setJ(String j) {
        J = j;
    }

    public String getPersoanaLegatura() {
        return persoanaLegatura;
    }


    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    public String getDate() {
        return rDate;
    }
}
