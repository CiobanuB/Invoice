package com.tim04.school.facturing.service.invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.client.ClientRepository;
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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

import static org.apache.logging.log4j.util.LambdaUtil.getAll;

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


    public String generateReport(String path) {
        try {

            List<Invoice> employees = invoiceRepository.findAll();
            List<Client> clients = clientService.findByUserID();
            Supplier theSupplier = supplierService.findSupplierbyUserMail();
            String thePath = path;
            // Compile the Jasper report from .jrxml to .japser
            InputStream stream = this.getClass().getResourceAsStream("/MainInvoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(stream);
            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            List<Map<String, Object>> objects = new ArrayList<>();
            parameters.put("createdBy", "Websparrow.org");
           /* for (Invoice invoice : employees) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("InvoiceID", invoice.getInvoiceID());
                item.put("services", invoice.getServices());
               *//*item.put("price", invoice.getTotalPrice());
              item.put("quantity", invoice.getPieces());
              item.put("quantity", invoice.getUnityMeasure());*//*
                objects.add(item);
            }*/
     /*       for (Client client : clients) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("clientName", client.getName());
                item.put("clientCif", client.getCif());
             *//*   item.put("clientRegDate", client.getRegDate());
                item.put("clientAdress", client.getAdress());*//*
                objects.add(item);
            }*/
            Map<String, Object> supplierItems = new HashMap<String, Object>();
            supplierItems.put("name", theSupplier.getName());
            supplierItems.put("regDate", theSupplier.getRegDate());
            supplierItems.put("mail", theSupplier.getMail());
            supplierItems.put("cifSupplier", theSupplier.getCifSupplier());
            supplierItems.put("adress", theSupplier.getAdress());
            supplierItems.put("bankAccount", theSupplier.getBankAccount());
            supplierItems.put("website", theSupplier.getWebsite());
            objects.add(supplierItems);
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

/*    public Map<String, Object> objectsMap()*/


}
