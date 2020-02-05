package com.tim04.school.facturing.service.supplier;

import com.tim04.school.facturing.persistence.company.Supplier;
import com.tim04.school.facturing.persistence.company.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    @Transactional
    public void save(String companyName, Date date, int cuiFiscal, String J, String persLegatura, String mail,String bankAccount,String adress) {
        Supplier theSupplier = new Supplier();
        theSupplier.setName(companyName);
        theSupplier.setrDate(date);
        theSupplier.setcuiFiscal(cuiFiscal);
        theSupplier.setJ(J);
        theSupplier.setPersoanaLegatura(persLegatura);
        theSupplier.setMail(mail);
        theSupplier.setAdress(adress);
        theSupplier.setBankAccount(bankAccount);
        supplierRepository.save(theSupplier);
    }

}
