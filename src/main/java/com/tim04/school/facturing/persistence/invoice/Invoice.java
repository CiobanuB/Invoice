package com.tim04.school.facturing.persistence.invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long InvoiceID;
    @Column(name = "invoiceSeries")
    private Integer invoiceSeries;
    @Column(name = "printDate")
    private String printDate;
    @Column(name = "clientName")
    private String clientName;
    @Column(name = "unityMeasure")
    private String unityMeasure;
    @Column(name = "sum")
    private Integer sum;
    @Column(name = "services")
    private String services;
    @Column(name = "pieces")
    private Integer pieces;
    @Column(name = "unitPrice")
    private Integer unitPrice;
    @Column(name = "totalPrice")
    private Integer totalPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="supplier_id")
    private Supplier supplier ;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client ;

    public Long getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(Long invoiceID) {
        InvoiceID = invoiceID;
    }

    public Integer getInvoiceSeries() {
        return invoiceSeries;
    }

    public void setInvoiceSeries(Integer invoiceSeries) {
        this.invoiceSeries = invoiceSeries;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }
    /* public Date getPrintDate() {
        Date date = (Date) this.printDate.clone();
        return date;
    }

    public int getMonthDate() {
        Date theDate = (Date) this.printDate.clone();
        LocalDate date = theDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date.getMonthValue();
    }

    public Date setPrintDate(String date) throws ParseException {
        SimpleDateFormat theDate = new SimpleDateFormat("dd-MM-yyyy");
        this.printDate = theDate.parse(date);
        return this.printDate;
    }*/

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUnityMeasure() {
        return unityMeasure;
    }

    public void setUnityMeasure(String unityMeasure) {
        this.unityMeasure = unityMeasure;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = this.unitPrice * this.pieces;
    }


    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    @Override
    public String toString() {
        return "Invoice{" +
                "InvoiceID=" + InvoiceID +
                ", invoiceSeries=" + invoiceSeries +
                ", printDate='" + printDate + '\'' +
                ", clientName='" + clientName + '\'' +
                ", unityMeasure=" + unityMeasure +
                ", sum=" + sum +
                ", services='" + services + '\'' +
                ", pieces=" + pieces +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
