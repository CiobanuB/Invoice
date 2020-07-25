package com.tim04.school.facturing.persistence.client;

import com.tim04.school.facturing.persistence.invoice.invoice.Invoice;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
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
    @ManyToOne
    @JoinColumn(name="supplier_id")
    private Supplier supplier;
    @OneToMany( mappedBy="client",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices = new ArrayList<>();



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    /*    public Date getRegDate() {
        return (Date) this.regDate.clone();
    }
    public String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(this.regDate);
        return date;
    }

    public Date setRegDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.regDate = dateFormat.parse(date);
        return this.regDate;
    }
    public String getCurrentDate() {
        SimpleDateFormat theDate = new SimpleDateFormat("dd-MM-yyyy");
        Date date = Calendar.getInstance().getTime();
        return theDate.format(date);

    }*/

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regDate='" + regDate + '\'' +
                ", mail='" + mail + '\'' +
                ", cif=" + cif +
                ", adress='" + adress + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }
}
