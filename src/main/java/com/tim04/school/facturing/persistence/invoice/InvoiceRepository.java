package com.tim04.school.facturing.persistence.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
   Invoice findInvoiceByClientName(String clientName);
   Invoice findInvoiceByInvoiceSeries(Integer invoiceSeries);
  /* Invoice findByPrintDateBeforeAndClientName(Date before,String clientName);
   List<Invoice> findClientNameDistinctByOrderByDateAsc();*/
   /*findAllByOrderByDateAsc*/
 /*  @Query("SELECT DISTINCT p.name FROM Invoice p ORDER BY p.printDate")
   List<Invoice>  getAllClientsByLastDate();*/

   List<Invoice> findAll();

}
