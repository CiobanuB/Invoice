package com.tim04.school.facturing.persistence.supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplierRepository  extends JpaRepository<Supplier,Long> {
    Supplier findSupplierByUserMail(String mail);

}
