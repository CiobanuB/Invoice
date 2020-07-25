package com.tim04.school.facturing.persistence.supplier;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.invoice.Invoice;
import com.tim04.school.facturing.persistence.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String cifSupplier;
    @Column(name = "Adress")
    private String adress;
    @Column(name = "bankAccount")
    private String bankAccount;
    @Column(name = "website")
    private String website;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user ;
    @OneToMany( mappedBy="supplier",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Client> clients = new ArrayList<>();
    @OneToMany( mappedBy="supplier",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices = new ArrayList<>();

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

    public String getCifSupplier() {
        return cifSupplier;
    }

    public void setCifSupplier(String cifSupplier) {
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }


    @Override
    public String toString() {
        return "Supplier{" +
                "supplierID=" + supplierID +
                ", name='" + name + '\'' +
                ", regDate='" + regDate + '\'' +
                ", mail='" + mail + '\'' +
                ", cifSupplier='" + cifSupplier + '\'' +
                ", adress='" + adress + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", website='" + website + '\'' +
                ", user=" + user +
                ", clients=" + clients +
                '}';
    }


}
