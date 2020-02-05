package com.tim04.school.facturing.persistence.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
   Invoice findInvoiceByCuiFiscal(int cuiFiscal);
   List<Invoice> findAll();

}
