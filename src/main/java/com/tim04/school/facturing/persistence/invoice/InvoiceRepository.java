package com.tim04.school.facturing.persistence.invoice;

import com.tim04.school.facturing.persistence.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findInvoiceByinvoiceSeries(Integer series);
    Optional<Invoice> findInvoiceById(Long id);
   Invoice findInvoiceByClientName(String clientName);

   @Query("SELECT DISTINCT a FROM Invoice a")
   List<Invoice> findDistinctClientsss();
  @Query("SELECT a FROM Invoice a WHERE (a.printDate,a.clientName) IN ( SELECT MAX(x.printDate),x.clientName FROM Invoice x GROUP BY x.client)")
  List<Invoice> findDistinctClients();
  @Query(" SELECT MAX(x.printDate) FROM Invoice x GROUP BY x.clientName")
  List<String> findDistinctOnes();
  Optional<List<Invoice>> findAllBySupplier(Supplier supplier);




}
