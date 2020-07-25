package com.tim04.school.facturing.service.client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.client.ClientRepository;
import com.tim04.school.facturing.persistence.invoice.invoice.Invoice;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.invoice.InvoiceService;
import com.tim04.school.facturing.service.supplier.SupplierService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    private final UserService userService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private InvoiceService invoiceService;


    @Autowired
    public ClientService(ClientRepository clientRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.userService = userService;
    }

    @Transactional
    public void save(String name, String regDate, int cif, String adress, String contactPerson/*, User user*/) throws ParseException {
        Client theClient = new Client();
        theClient.setName(name);
        theClient.setRegDate(regDate);
        theClient.setCif(cif);
        theClient.setAdress(adress);
        theClient.setContactPerson(contactPerson);
        /*  theClient.setUser(user);*/
        clientRepository.save(theClient);
    }

    public List<Client> findAllClientsByUser() {
        Supplier supplier = supplierService.getTheSupplier();
        if (supplier == null) return null;
        List<Client> clientList = supplier.getClients();
        return clientList;
    }

    public Client getClientByMail(String mail) {
        Client client = clientRepository.findByMail(mail);
        return client;
    }

    @Transactional
    public void deleteClient(Client client) {
        try {
            clientRepository.delete(client);
        } catch (NullPointerException e) {
            System.out.print("There is no client available to be deleted !");
        }
    }

    @Transactional
    public void save(@Valid Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void addClient(Client client) {
        User currentUser = userService.findLogged();
        Supplier supplier = supplierService.getTheSupplier();
        client.setSupplier(supplier);
        client.setUser(currentUser);
        client.setRegDate(client.getRegDate());
        clientRepository.save(client);
    }

    public Client findClient(Client client) {
        User currentUser = userService.findLogged();
        Client findClient = clientRepository.findByNameAndUserId(client.getName(), currentUser.getId());
        return findClient;
    }
    public Optional<Client> findClientByName(String name){
        Supplier supplier = supplierService.getTheSupplier();
        Optional<Client> optionalClient = clientRepository.findByNameAndSupplier(name,supplier);
        if(!optionalClient.isPresent()) return Optional.empty();
        return optionalClient;
    }
    public Optional<Client> findClientByCif(Integer cif){
        Supplier supplier = supplierService.getTheSupplier();
        Optional<Client> optionalClient = clientRepository.findByCifAndSupplier(cif,supplier);
        if(!optionalClient.isPresent()) return Optional.empty();
        return optionalClient;
    }


    public void updateClient(Client client) {
        Optional<Client> optionalClient = clientRepository.findById(client.getId());
        Client findClient = optionalClient.get();
        findClient.setName(client.getName());
        findClient.setRegDate(client.getRegDate());
        findClient.setMail(client.getMail());
        findClient.setCif(client.getCif());
        findClient.setAdress(client.getAdress());
        findClient.setContactPerson(client.getContactPerson());
        clientRepository.save(findClient);
    }

    public Map<String, Object> clientMap(Invoice invoice, Map<String, Object> clientItems) {
        Optional<Invoice> optionalInvoice = invoiceService.getInvoiceSeries(invoice.getInvoiceSeries());
        if (optionalInvoice.isPresent()) {
            Invoice findInvoice = optionalInvoice.get();
            Client client = findInvoice.getClient();
            clientItems.put("clientName", client.getName());
            clientItems.put("clientCif", client.getCif());
            clientItems.put("clientRegDate", client.getRegDate());
            clientItems.put("clientAdress", client.getAdress());
        }
        return clientItems;
    }

    public Optional<Client> findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) return optionalClient;
        return Optional.empty();
    }

    @Transactional
    public void deleteById(Long clientId) {
        Optional<Client> findClient = clientRepository.findById(clientId);
    }


}
