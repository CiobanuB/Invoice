package com.tim04.school.facturing.service.invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.Invoice;
import com.tim04.school.facturing.persistence.invoice.InvoiceRepository;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.client.ClientService;
import com.tim04.school.facturing.service.supplier.SupplierService;
import com.tim04.school.facturing.user.UserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SupplierService supplierService;


    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    @Transactional
    private void save(Integer invoiceSeries, Date printDate, String clientName, int unityMeasure, int sum, String services, int pieces, int vat) {
        Invoice theInvoice = new Invoice();
        theInvoice.setInvoiceSeries(invoiceSeries);
        theInvoice.setPrintDate(printDate);
        theInvoice.setClientName(clientName);
        theInvoice.setUnityMeasure(unityMeasure);
        theInvoice.setSum(sum);
        theInvoice.setServices(services);
        theInvoice.setPieces(pieces);
        invoiceRepository.save(theInvoice);
    }

    @Transactional(readOnly = true)
    public Invoice findInvoice(String clientName) {
        Invoice theInvoice = invoiceRepository.findInvoiceByClientName(clientName);
        return theInvoice;
    }

    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        List<Invoice> theList = invoiceRepository.findAll();
        return theList;
    }

    public void generateClientFolder(String path) {
        File file = new File(path);
        User user = userService.findLogged();
        List<Client> clientList = clientService.findByUserID();
        for (Client client : clientList) {
            boolean flag = false;
            for (File theFile : file.listFiles()) {
                if (theFile.isDirectory() && theFile.getName().equals(client.getName())) {
                    flag = true;
                }
            }
            if (!flag) {
                File newFile = new File(path + "\\" + client.getName());
                newFile.mkdir();
            }
        }
    }


    public String generateReport(String path, Invoice invoice,String clientName) {
        try {

            Supplier theSupplier = supplierService.getSupplier();
            Map<String, Object> supplierMap = supplierService.supplierMap(theSupplier);
            Map<String, Object> clientMap = clientService.clientMap(clientName);

            Map<String, Object> invoiceItem = new HashMap<String, Object>();
            invoiceItem.put("InvoiceID", invoice.getInvoiceID());
            invoiceItem.put("services", invoice.getServices());
            invoiceItem.put("price", invoice.getTotalPrice());
            invoiceItem.put("quantity", invoice.getPieces());
            invoiceItem.put("unityMeasure", invoice.getUnityMeasure());
            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Websparrow.org");
            List<Map<String, Object>> objects = new ArrayList<>();
            objects.add(supplierMap);
            objects.add(supplierMap);
            objects.add(clientMap);

            String thePath = path;
            // Compile the Jasper report from .jrxml to .japser
            InputStream stream = this.getClass().getResourceAsStream("/MainInvoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(stream);

            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(objects, false);
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, thePath + "\\testing22.pdf");
            System.out.println("Done");
            return "Report successfully generated @path= " + thePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error--> check the console log";
        }
    }

    public String getCurrentMonth() {
        String[] lunileAnului = {"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"};
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        return lunileAnului[month - 1];
    }


    public Map<String, Object> invoiceMap( Invoice invoice) {
        Map<String, Object> invoiceItems = new HashMap<String, Object>();
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("InvoiceID", invoice.getInvoiceID());
            item.put("services", invoice.getServices());
            item.put("price", invoice.getTotalPrice());
            item.put("quantity", invoice.getPieces());
            item.put("unityMeasure", invoice.getUnityMeasure());
        return invoiceItems;
    }
    @Transactional(readOnly = true)
    public List<Invoice> getListInvoice(){
        List<Invoice> getList = invoiceRepository.findAll();
        return getList;
    }

/*    public Map<String, Supplier> invoiceMap(List<Supplier> invoiceList) {
        Map<String, Supplier> invoiceItems = new HashMap<String, Supplier>();
        for (Invoice invoice : invoiceList) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("InvoiceID", invoice.getInvoiceID());
            item.put("services", invoice.getServices());
            item.put("price", invoice.getTotalPrice());
            item.put("quantity", invoice.getPieces());
            item.put("unityMeasure", invoice.getUnityMeasure());
        }
        return invoiceItems;
    }*/


}
