package com.tim04.school.facturing.service.client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.client.ClientRepository;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.persistence.user.UserRepository;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void save(String name, String regDate, int cif, String adress, String contactPerson, Long userID) {
        Client theClient = new Client();
        theClient.setName(name);
        theClient.setRegDate(regDate);
        theClient.setCif(cif);
        theClient.setAdress(adress);
        theClient.setContactPerson(contactPerson);
        theClient.setUserID(userID);
        clientRepository.save(theClient);
    }


    public List<Client> findByUserID(){
        User user  = userService.findLogged();
        return clientRepository.findByUserID(user.getUserID());
    }
    public Client getClient(){
        return clientRepository.findByName("bog");
    }

    @Transactional
    public void save(Client client) {
         clientRepository.save(client);
    }
}
