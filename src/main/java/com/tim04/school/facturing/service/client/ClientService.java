package com.tim04.school.facturing.service.client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.client.ClientRepository;
import com.tim04.school.facturing.persistence.invoice.Invoice;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Client getClientByMail(String mail)
    {
        Client client = clientRepository.findByMail(mail);
        return client;
    }
    public Client getClient(){
        return clientRepository.findByName("bog");
    }

    @Transactional
    public void save(Client client) {
         clientRepository.save(client);
    }

    public Client updateClient(Client client)
    {
        Client findClient = clientRepository.findByMail(client.getMail());
        findClient.setName(client.getName());
        findClient.setRegDate(client.getRegDate());
        findClient.setMail(client.getMail());
        findClient.setCif(client.getCif());
        findClient.setAdress(client.getAdress());
        findClient.setContactPerson(client.getContactPerson());
        return findClient;
    }
    public Map<String, Object> clientMap(String name) {
        User user  = userService.findLogged();
       Client client = clientRepository.findByNameAndUserID(name,user.getUserID());
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("clientName", client.getName());
            item.put("clientCif", client.getCif());
            item.put("clientRegDate", client.getRegDate());
            item.put("clientAdress", client.getAdress());
        return item;
    }

}
