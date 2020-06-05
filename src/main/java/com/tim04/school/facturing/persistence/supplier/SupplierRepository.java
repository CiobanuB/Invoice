package com.tim04.school.facturing.persistence.supplier;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SupplierRepository  extends JpaRepository<Supplier,Long> {
   Supplier  findSupplierByUser(User user);
   //Optional<Supplier>  findSupplierByUser(User user);
   Supplier  findByCifSupplier(String cif);
   //Supplier  findSupplierByUserMail(String mail);
}
