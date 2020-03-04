package com.tim04.school.facturing.controller.Client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("add-edit-clients")
public class AddEditClients {
    @Autowired
    private ClientService clientService;

   /* @RequestMapping(value = "/{userID}" ,method = RequestMethod.GET)
    public ModelAndView getClients(@PathVariable(value = "userID", required = false) Long userid) {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clients = clientService.getAll();
        for (Client client : clients) {
            System.out.println(client.toString());
            userid = client.getUserID();
        }
        modelAndView.setViewName("Clients/AddEditClients");
        modelAndView.addObject("clients",clients);
        return modelAndView;
    }*/
    @RequestMapping(value = "/{userID}", method = RequestMethod.GET)
    public ModelAndView getClients(@RequestParam (value = "cif",required = false) int cif, int CUI) {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clients = clientService.getAll();
        for (Client client : clients) {
            System.out.println(client.toString());
            cif = client.getCif();
        }
        modelAndView.setViewName("Clients/AddEditClients");
        modelAndView.addObject("clients",clients);
        return modelAndView;
    }
    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView getClients() {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clients = clientService.getAll();
        for (Client client : clients) {
            System.out.println(client.toString());
        }
        modelAndView.setViewName("Clients/AddEditClients");
        modelAndView.addObject("clients",clients);
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView saveClient(@ModelAttribute(value = "theClient") Client client) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Clients/AddEditClients");
        clientService.save(client);
        modelAndView.addObject(client);
        System.out.println(client);
        return modelAndView;
    }

}
