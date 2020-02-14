package com.tim04.school.facturing.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findByName(String name);
    Client findByCif(int cui);
    List<Client> findAll();


}
