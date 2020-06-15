package com.tim04.school.facturing.service.supplier;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.Invoice;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.supplier.SupplierRepository;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.invoice.InvoiceService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierRepository getSupplierRepository() {
        return supplierRepository;
    }

    @Transactional
    public void save(Supplier supplier) {
        User loggedUser = userService.findLogged();
        supplier.setUser(loggedUser);
        supplierRepository.save(supplier);
    }
    @Transactional
    public void updateSupplier(Supplier supplier) {
        User loggedUser = userService.findLogged();
        Supplier findSupplier = supplierRepository.findSupplierByUser(loggedUser);
        findSupplier.setBankAccount(supplier.getBankAccount());
        findSupplier.setCifSupplier(supplier.getCifSupplier());
        findSupplier.setMail(supplier.getMail());
        findSupplier.setName(supplier.getName());
        findSupplier.setRegDate(supplier.getRegDate());
        findSupplier.setAdress(supplier.getAdress());
        findSupplier.setWebsite(supplier.getWebsite());
        findSupplier.setUser(loggedUser);
        supplierRepository.save(findSupplier);
    }

    public Supplier getTheSupplier()
    {
        User user = userService.findLogged();
        Supplier supplier = supplierRepository.findSupplierByUser(user);
        if(supplier ==null) return null;
        return supplier;
    }
    public Supplier setFields(Supplier supplier)
    {
        User theUser = userService.findLogged();
        supplier.setUser(theUser);
        return supplier;
    }

    public Map<String, Object> supplierMap(Invoice invoice,Map<String,Object> supplierItems) {
        Optional<Invoice> optionalInvoice = invoiceService.getInvoiceSeries(invoice.getInvoiceSeries());
        Invoice findInvoice = optionalInvoice.get();
        Supplier supplier = findInvoice.getSupplier();
        User loggedUser = userService.findLogged();
        if(optionalInvoice.isPresent()) {
            supplierItems.put("name", supplier.getName());
            supplierItems.put("regDate", supplier.getRegDate());
            supplierItems.put("mail", supplier.getMail());
            supplierItems.put("cifSupplier", supplier.getCifSupplier());
            supplierItems.put("adress", supplier.getAdress());
            supplierItems.put("bankAccount", supplier.getBankAccount());
            supplierItems.put("website", supplier.getWebsite());
            supplierItems.put("firstName", loggedUser.getFirstName());
        }
        return supplierItems;
    }

}
