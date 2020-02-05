package com.tim04.school.facturing.persistence.invoice;

import javax.persistence.*;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue
    private Long InvoiceID;
    @Column(name = "invoiceSeries")
    private Integer invoiceSeries;
    @Column(name = "printDate")
    private String printDate;
    @Column(name = "cuiFiscal")
    private Integer cuiFiscal;
    @Column(name = "unityMeasure")
    private Integer unityMeasure;
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

    public Integer getCuiFiscal() {
        return cuiFiscal;
    }

    public void setCuiFiscal(Integer cuiFiscal) {
        this.cuiFiscal = cuiFiscal;
    }

    public Integer getUnityMeasure() {
        return unityMeasure;
    }

    public void setUnityMeasure(Integer unityMeasure) {
        this.unityMeasure = unityMeasure;
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

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "InvoiceID=" + InvoiceID +
                ", invoiceSeries=" + invoiceSeries +
                ", printDate='" + printDate + '\'' +
                ", cuiFiscal=" + cuiFiscal +
                ", unityMeasure=" + unityMeasure +
                ", sum=" + sum +
                ", services='" + services + '\'' +
                ", pieces=" + pieces +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
