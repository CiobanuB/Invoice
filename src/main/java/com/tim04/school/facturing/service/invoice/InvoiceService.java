package com.tim04.school.facturing.service.invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.client.ClientRepository;
import com.tim04.school.facturing.persistence.invoice.Invoice;
import com.tim04.school.facturing.persistence.invoice.InvoiceRepository;
import com.tim04.school.facturing.persistence.user.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

@Repository
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;


    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    @Transactional
    private void save(Integer invoiceSeries, String printDate, int cuiFiscal, int unityMeasure, int sum, String services, int pieces, int vat) {
        Invoice theInvoice = new Invoice();
        theInvoice.setInvoiceSeries(invoiceSeries);
        theInvoice.setPrintDate(printDate);
        theInvoice.setCuiFiscal(cuiFiscal);
        theInvoice.setUnityMeasure(unityMeasure);
        theInvoice.setSum(sum);
        theInvoice.setServices(services);
        theInvoice.setPieces(pieces);
        invoiceRepository.save(theInvoice);
    }

    @Transactional(readOnly = true)
    public Invoice findInvoice(int cif) {
        Invoice theInvoice = invoiceRepository.findInvoiceByCuiFiscal(cif);
        return theInvoice;
    }

    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        List<Invoice> theList = invoiceRepository.findAll();
        return theList;
    }

    public String generateReport() {
        try {

            List<Invoice> employees = invoiceRepository.findAll();
            String path = "C:\\Users\\Ciobanu\\Desktop\\pdfs\\";


            // Compile the Jasper report from .jrxml to .japser
            InputStream stream = this.getClass().getResourceAsStream("/InvoiceClient.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(stream);


            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            List<Map<String, Object>> objects = new ArrayList<>();
            parameters.put("createdBy", "Websparrow.org");
            for (Invoice invoice : employees) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("InvoiceID", invoice.getInvoiceID());
                item.put("services", invoice.getServices());
//                item.put("price", invoice.getPrice());
//                item.put("quantity", invoice.getQuantity());
//                item.put("categoryName", invoice.getCategoryName());
                objects.add(item);
            }
            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(objects, false);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                    jrBeanCollectionDataSource);

            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\Emp-Rpt-Database.pdf");

            System.out.println("Done");

            return "Report successfully generated @path= " + path;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error--> check the console log";
        }
    }


}
