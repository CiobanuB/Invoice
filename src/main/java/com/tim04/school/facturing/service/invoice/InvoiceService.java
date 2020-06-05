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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
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
    private InvoiceService invoiceService;


    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

/*    @Transactional
    private void save(Integer invoiceSeries, String printDate, String clientName, int unityMeasure, int sum, String services, int pieces, int vat) throws ParseException {
        Invoice theInvoice = new Invoice();
        theInvoice.setInvoiceSeries(invoiceSeries);
        theInvoice.setPrintDate(printDate);
        theInvoice.setClientName(clientName);
        theInvoice.setUnityMeasure(unityMeasure);
        theInvoice.setSum(sum);
        theInvoice.setServices(services);
        theInvoice.setPieces(pieces);
        invoiceRepository.save(theInvoice);
    }*/

    @Transactional
    public void save(Invoice invoice) throws ParseException {
        Supplier supplier = supplierService.getTheSupplier();
        Optional<Client> optionalClient = clientService.findClientByName(invoice.getClientName());
        Client client = optionalClient.get();
        if (optionalClient.isPresent()) invoice.setClient(client);
        invoice.setSupplier(supplier);
        invoiceRepository.save(invoice);
    }

    public Invoice findInvoice(String clientName) {
        Invoice theInvoice = invoiceRepository.findInvoiceByClientName(clientName);
        return theInvoice;
    }

    public List<Invoice> findAll() {
        List<Invoice> theList = invoiceRepository.findAll();
        return theList;
    }

    public void generateClientFolder(String path) {
        List<Client> clientList = clientService.findAllClientsByUser();
        Path locationPath = Paths.get(path);
        if (Files.isDirectory(locationPath)) {
            System.out.println(Files.isDirectory(locationPath));
            File file = new File(path);
            generateBackupFolders(clientList, file, path);
        } else {
            File makeFolder = new File(path);
            makeFolder.mkdir();
            generateBackupFolders(clientList, makeFolder, path);
        }
    }

    public void generateBackupFolders(List<Client> clientList, File file, String path) {
        for (Client client : clientList) {
            boolean flag = false;
            if (file.length() != 0) {
                for (File theFile : file.listFiles()) {
                    if (theFile.isDirectory() && theFile.getName().equals(client.getName())) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                File newFile = new File(path + "\\" + client.getName());
                newFile.mkdir();
            }
        }
    }


    public void savePathUser(User user) {
        User loggedUser = userService.findLogged();
        invoiceService.generateClientFolder(user.getDefaultPath());
        loggedUser.setDefaultPath(user.getDefaultPath());
        userService.save(loggedUser);
    }

    /* public String generateReport(String path, Invoice invoice,String clientName) { */
    public String generateReport(String path, Invoice invoice, String clientName) {
        try {
            Supplier theSupplier = supplierService.getTheSupplier();
            Client client = invoice.getClient();
            Map<String, Object> supplierMap = supplierService.supplierMap(invoice);
            Map<String, Object> clientMap = clientService.clientMap(invoice);
            Map<String, Object> invoiceMap = invoiceService.invoiceMap(invoice);


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
            JasperExportManager.exportReportToPdfFile(jasperPrint, thePath + "\\" + invoice.getClientName());
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


    public Map<String, Object> invoiceMap(Invoice invoice) {
        Map<String, Object> invoiceItems = new HashMap<>();
        Optional<Invoice> optionalInvoice = invoiceRepository.findInvoiceByinvoiceSeries(invoice.getInvoiceSeries());
        if(optionalInvoice.isPresent()){
            invoiceItems.put("InvoiceID", invoice.getInvoiceID());
            invoiceItems.put("services", invoice.getServices());
            invoiceItems.put("price", invoice.getTotalPrice());
            invoiceItems.put("quantity", invoice.getPieces());
            invoiceItems.put("unityMeasure", invoice.getUnityMeasure());
        }
        return invoiceItems;
    }

    @Transactional(readOnly = true)
    public List<Invoice> getListInvoice() {
        List<String> clients = invoiceRepository.findDistinctOnes();
        List<Invoice> getList = invoiceRepository.findDistinctClients();

        return getList;
    }

    public Optional<List<Invoice>> getAllInvoices() {
        Supplier supplier = supplierService.getTheSupplier();
        Optional<List<Invoice>> getAllInvoices = invoiceRepository.findAllBySupplier(supplier);
        if (!getAllInvoices.isPresent()) return Optional.empty();
        return getAllInvoices;
    }
    public Optional<Invoice> getInvoiceSeries(Integer seriesId) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findInvoiceByinvoiceSeries(seriesId);
        if (!optionalInvoice.isPresent()) return Optional.empty();
        return optionalInvoice;
    }

    public List<Invoice> distinctInvoices() {
        List<Invoice> distinctClients = invoiceRepository.findDistinctClients();
        return distinctClients;
    }

}
