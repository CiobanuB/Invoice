package com.tim04.school.facturing.service.invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.invoice.Invoice;
import com.tim04.school.facturing.persistence.invoice.invoice.InvoiceRepository;
import com.tim04.school.facturing.persistence.invoice.invoiceSeries.InvoiceSeries;
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
        InvoiceSeries invoiceSeries = new InvoiceSeries();
        Supplier supplier = supplierService.getTheSupplier();
        Optional<Client> optionalClient = clientService.findClientByName(invoice.getClientName());
        Client client = optionalClient.get();
        if (optionalClient.isPresent()) invoice.setClient(client);
        invoice.setInvoiceSeries(invoiceSeries);
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
        if (clientList != null) {
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

    public String generateReport(Invoice invoice) {
        try {
            Supplier theSupplier = supplierService.getTheSupplier();
            Client client = invoice.getClient();
            User loggedUser = theSupplier.getUser();
            String path = loggedUser.getDefaultPath();

            Map<String, Object> invoiceItems = new HashMap<>();

            Map<String, Object> clientMap = clientService.clientMap(invoice, invoiceItems);
            Map<String, Object> invoiceMap = invoiceService.invoiceMap(invoice, invoiceItems);
            Map<String, Object> supplierMap = supplierService.supplierMap(invoice, invoiceItems);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Websparrow.org");
            List<Map<String, Object>> objects = new ArrayList<>();
            objects.add(invoiceItems);

            // Compile the Jasper report from .jrxml to .japser
            InputStream stream = this.getClass().getResourceAsStream("/MainInvoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(stream);

            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(objects, false);
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
            generateClientFolder(path);
            deletePossibleFile(path, invoice.getClientName(), getCurrentDate());
            // Export the report to a PDF file
            /*JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\" + invoice.getClientName()+ " "+ getCurrentDate());*/
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\" + invoice.getClientName() + "\\" + invoice.getClientName() + " " + getCurrentDate() + ".pdf");

            return "Report successfully generated @path= " + path;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error--> check the console log";
        }
    }

    public String getCurrentDate() {
        String[] lunileAnului = {"Ianuarie ", "Februarie ", "Martie ", "Aprilie ", "Mai ", "Iunie ", "Iulie ", "August ", "Septembrie ", "Octombrie ", "Noiembrie ", "Decembrie "};
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        return lunileAnului[month - 1] + year;
    }
    public Integer currentYear(){
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        return year;
    }

    public void deletePossibleFile(String path, String clientName, String currentDate) {
        String allPath = path + clientName + currentDate + ".pdf";
        File file = new File(allPath);
        if (file.isFile()) {
            file.delete();
        }
    }

    public Map<String, Object> invoiceMap(Invoice invoice, Map<String, Object> invoiceItems) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findInvoiceByInvoiceSeries(invoice.getInvoiceSeries());
        if (optionalInvoice.isPresent()) {
            invoiceItems.put("invoiceID", invoice.getId());
            invoiceItems.put("services", invoice.getServices());
            invoiceItems.put("unityMeasure", invoice.getUnityMeasure());
            invoiceItems.put("pieces", invoice.getPieces());
            invoiceItems.put("unitPrice", invoice.getUnitPrice());
            invoiceItems.put("totalPrice", invoice.getSum());
            invoiceItems.put("printDate", invoice.getPrintDate());
            invoiceItems.put("invoiceSeries", invoice.getInvoiceSeries());
            System.out.println(invoice.getInvoiceSeries());
        }
        return invoiceItems;
    }

    public List<Invoice> getListInvoice() {
        Supplier supplier = supplierService.getTheSupplier();
        List<Invoice> getList = invoiceRepository.findDistinctClients(supplier);

        return getList;
    }

    public Optional<List<Invoice>> getAllInvoices() {
        Supplier supplier = supplierService.getTheSupplier();
        Optional<List<Invoice>> getAllInvoices = invoiceRepository.findAllBySupplier(supplier);
        if (!getAllInvoices.isPresent()) return Optional.empty();
        return getAllInvoices;
    }

    public Optional<Invoice> getInvoiceSeries(InvoiceSeries invoiceseries) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findInvoiceByInvoiceSeries(invoiceseries);
        if (!optionalInvoice.isPresent()) return Optional.empty();
        return optionalInvoice;
    }

 /*   public List<Invoice> distinctInvoices() {
        Supplier supplier = supplierService.getTheSupplier();
        List<Invoice> distinctClients = invoiceRepository.findDistinctClients(supplier);
        return distinctClients;
    }*/

    public Integer numberInvoiced() {
        Supplier supplier = supplierService.getTheSupplier();
        Integer invoiceDistinctDates = invoiceRepository.distinctNumberInvoicesCurrentMonthAndYear(supplier);
       /* Integer distinctClients = invoiceRepository.distinctClients(supplier);
        Integer numberToBeInvoiced = distinctClients - invoiceDistinctDates;*/
        return invoiceDistinctDates;
    }

    public Integer clientNumbers() {
        Supplier supplier = supplierService.getTheSupplier();
        Integer distinctClients = invoiceRepository.distinctClients(supplier);
        return distinctClients;
    }

    public Optional<Invoice> findById(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isPresent()) return optionalInvoice;
        return Optional.empty();
    }

    @Transactional
    public void deleteInvoice(Invoice invoice) {
        try {
            invoiceRepository.delete(invoice);
        } catch (NullPointerException e) {
            System.out.print("There is no invoice available to be deleted !");
        }
    }

    public void updateInvoice(Invoice invoice) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoice.getId());
        Invoice findInvoice = optionalInvoice.get();
        findInvoice = invoice;
        invoiceRepository.save(findInvoice);
    }
    public Map<String,Object> statisticsItems(){
        Map<String,Object> items = new HashMap<String,Object>();
        Supplier supplier = supplierService.getTheSupplier();
        Integer distinctClients = invoiceRepository.distinctClients(supplier);
        items.put("Number Clientss",distinctClients);
        Integer invoiceDistinctCurrentMonth = invoiceRepository.distinctNumberInvoicesCurrentMonthAndYear(supplier);
        items.put("Invoiced Current Month",invoiceDistinctCurrentMonth);
        Integer totalInvoices = invoiceRepository.countIdBySupplier(supplier);
        items.put("Total number of invoices",totalInvoices);
        Integer invoicesCurrentYear = invoiceRepository.distinctNumberInvoicesCurrentYear(supplier);
        items.put("Invoiced in year " + currentYear(),invoicesCurrentYear);

        return items;
    }

}
