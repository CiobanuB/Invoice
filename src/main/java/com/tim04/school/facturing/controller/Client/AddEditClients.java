package com.tim04.school.facturing.controller.Client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("add-edit-clients")
public class AddEditClients {
    @Autowired
    private ClientService clientService;

    @RequestMapping(method = RequestMethod.GET)
    public String getClients(Model model) {
        List<Client> clients = clientService.getAll();
        for (Client client : clients) {
            System.out.println(client.toString());
        }
        model.addAttribute("clients", clients);
        return "Clients/AddEditClients";
    }

    @PostMapping("/saveClient")
    public String saveClient(@ModelAttribute("client") Client client) {
        return "Clients/AddEditClients";
    }

}
