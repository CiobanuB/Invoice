package com.tim04.school.facturing.persistence.invoice.invoiceSeries;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.invoice.Invoice;
import com.tim04.school.facturing.persistence.supplier.Supplier;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "InvoiceSeries")
public class InvoiceSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne( cascade=CascadeType.ALL)
    @JoinColumn(name="invoice_id")
    private Invoice invoice;
    @Column(name = "invoiceSeries")
    private Integer invoiceSeries;
    @Column(name = "currentYear")
    private Integer currentYear;
    @Column(name = "prefix")
    private String prefix;

    public static int findCurrentYear() {
        Date currentDate = new Date();
        LocalDate date = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date.getYear();
    }


    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Integer getInvoiceSeries() {
        return invoiceSeries;
    }

    public void setInvoiceSeries(Integer invoiceSeries) {
        this.invoiceSeries = invoiceSeries;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        if(getCurrentYear() > currentYear) invoiceSeries = 1;
        else {
            invoiceSeries++;
        }

        this.currentYear = getCurrentYear();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
