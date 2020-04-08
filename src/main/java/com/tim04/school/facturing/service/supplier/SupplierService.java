package com.tim04.school.facturing.service.supplier;

import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.supplier.SupplierRepository;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierRepository getSupplierRepository() {
        return supplierRepository;
    }

    @Transactional
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public Supplier getSupplier()
    {
        User user = userService.findLogged();
        Supplier supplier = supplierRepository.findSupplierByUserMail(user.getMail());
        return supplier;
    }
    public Supplier setFields(Supplier supplier)
    {
        User theUser = userService.findLogged();
        Supplier theSupplier = supplierRepository.findSupplierByUserMail(theUser.getMail());
        theSupplier.setName(supplier.getName());
        theSupplier.setRegDate(supplier.getRegDate());
        theSupplier.setMail(supplier.getMail());
        theSupplier.setCifSupplier(supplier.getCifSupplier());
        theSupplier.setAdress(supplier.getAdress());
        theSupplier.setBankAccount(supplier.getBankAccount());
        theSupplier.setWebsite(supplier.getWebsite());
        theSupplier.setUserMail(theUser.getMail());
        return theSupplier;
    }

    public Map<String, Object> supplierMap(Supplier supplier) {

        Map<String, Object> supplierItems = new HashMap<String, Object>();
        supplierItems.put("name", supplier.getName());
        supplierItems.put("regDate", supplier.getRegDate());
        supplierItems.put("mail", supplier.getMail());
        supplierItems.put("cifSupplier", supplier.getCifSupplier());
        supplierItems.put("adress", supplier.getAdress());
        supplierItems.put("bankAccount", supplier.getBankAccount());
        supplierItems.put("website", supplier.getWebsite());
        return supplierItems;
    }

}
