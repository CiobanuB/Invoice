package com.tim04.school.facturing.persistence.invoice.invoice;

import com.tim04.school.facturing.persistence.invoice.invoiceSeries.InvoiceSeries;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findInvoiceByInvoiceSeries(InvoiceSeries invoiceSeries);

    Optional<Invoice> findInvoiceById(Long id);

    Invoice findInvoiceByClientName(String clientName);

    List<Invoice> findAll();


    @Query("SELECT a FROM Invoice a WHERE a.printDate = ( SELECT MAX(x.printDate) FROM Invoice x WHERE a.clientName = x.clientName AND a.supplier = :theSupplier)")
    List<Invoice> findDistinctClients(@Param("theSupplier") Supplier supplier);

    @Query("SELECT COUNT(a.printDate) FROM Invoice a WHERE  EXTRACT(YEAR FROM a.printDate)  = YEAR(NOW())  AND EXTRACT(MONTH FROM a.printDate)  = MONTH(NOW()) AND a.supplier = :theSupplier")
    Integer distinctNumberInvoicesCurrentMonthAndYear(@Param("theSupplier") Supplier supplier);

    @Query("SELECT COUNT(a.printDate) FROM Invoice a WHERE EXTRACT(YEAR FROM a.printDate)  = YEAR(NOW()) AND a.supplier = :theSupplier")
    Integer distinctNumberInvoicesCurrentYear(@Param("theSupplier") Supplier supplier);


    @Query("SELECT  COUNT(DISTINCT x.clientName) FROM Invoice x where x.supplier= :theSupplier ")
    Integer distinctClients(@Param("theSupplier") Supplier supplier);

    Optional<List<Invoice>> findAllBySupplier(Supplier supplier);


    Integer countIdBySupplier(Supplier supplier);
}
