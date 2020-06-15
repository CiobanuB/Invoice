package com.tim04.school.facturing.persistence.client;

import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNameAndSupplier(String name, Supplier supplier);

   Optional<Client> findByCif(Integer cif);

    Client findByMail(String mail);

    List<Client> findAll();

    List<Client> findAllByUser(User user);

    Client findByNameAndUserId(String name, Long id);

    Optional<Client> findByIdAndUser(Long id, User user);

    Optional<Client> findById(Long id);

    Optional<Client> findByCifAndSupplier(Integer cif, Supplier supplier);
}
