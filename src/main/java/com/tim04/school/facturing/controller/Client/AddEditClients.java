package com.tim04.school.facturing.controller.Client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.client.ClientService;
import com.tim04.school.facturing.service.supplier.SupplierService;
import com.tim04.school.facturing.user.UserService;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("clients")
public class AddEditClients {
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping()
    public ModelAndView getClients() {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clients = clientService.findAllClientsByUser();
        Client client = new Client();
        modelAndView.setViewName("Clients/AddEditClients");
        modelAndView.addObject("clients", clients);
        modelAndView.addObject("client", client);
        return modelAndView;
    }

    /*    @GetMapping(value = {"/{clientId}/edit"})
        public ModelAndView showEdit(@PathVariable("clientId") Long clientId) {
            ModelAndView modelAndView = new ModelAndView();

            Optional<Client> optionalClient = clientService.findById(clientId);

            if (!optionalClient.isPresent()) {
                *//*modelAndView.addObject("confirmationMessage", "Client not found ");*//*
        } else {
            modelAndView.addObject("client", optionalClient.get());

        }
        modelAndView.setViewName("Clients/EditClients");
        return modelAndView;
    }*/
  /*  @GetMapping(value = {"/{clientId}/edit"})
    public String editClientById(Model model, @PathVariable(value = "clientId") Long id) {

        Optional<Client> optionalClient = clientService.findById(id);
        model.addAttribute("client", optionalClient.get());
        return "Clients/EditClients";
    }
*/
   /* @GetMapping(value = {"/{id}"})
    public ModelAndView showEdit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Client> optionalClient = clientService.findById(id);
        Client getClient = optionalClient.get();

        if (!optionalClient.isPresent()) {
            modelAndView.addObject("confirmationMessage", "Client not found ");
        } else {
            modelAndView.addObject("clientName", getClient.getName());
            modelAndView.addObject("client", optionalClient.get());
        }
        modelAndView.setViewName("Clients/EditClients");
        return modelAndView;
    }*/
    @PostMapping()
    public ModelAndView addClient(@ModelAttribute(value = "client") Client client, BindingResult bindingResult, RedirectAttributes redirectAttrs)  {
        ModelAndView modelAndView = new ModelAndView();
        Client findClient = clientService.findClient(client);
        if (findClient != null) {
            /*redirectAttrs.addFlashAttribute("confirmationMessage","There is another client registered with " + findClient.getName() + " name");*/
            bindingResult.rejectValue("name", "error.client", "There is another client registered with " + findClient.getName() + " name");
            /*  modelAndView.setViewName("redirect:/clients");*/
            //modelAndView.setViewName("/clients");
            /*bindingResult.reject("name");*/
        }
        if (bindingResult.hasErrors()) {
            redirectAttrs.addFlashAttribute("confirmationMessage", "Client " + client.getName() + " already exists !");
            /*modelAndView.setViewName("redirect:/clients");*/
            modelAndView.setViewName("Clients/AddEditClients");
            /*modelAndView.setViewName("Clients/AddEditClients");*/
            /*modelAndView.setViewName("/clients");*/
        } else {
            /*modelAndView.setViewName("Clients/AddEditClients");*/
            /*modelAndView.setViewName("redirect:/clients");*/
            clientService.addClient(client);
          /*  modelAndView.addObject("successMessage", "Client has been created succesfully !");
            modelAndView.addObject("client", new Client());*/
            redirectAttrs.addFlashAttribute("confirmationMessage", "Client " + client.getName() + " has been created succesfully !");
            redirectAttrs.addAttribute("client", new Client());
            modelAndView.setViewName("redirect:/clients");
        }
        return modelAndView;
    }

    @DeleteMapping
    public ModelAndView deleteClient(@ModelAttribute("client") Client client, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Client findClient = clientService.getClientByMail(client.getMail());
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Clients/AddEditClients");
        } else {
            clientService.deleteClient(client);
            modelAndView.setViewName("Clients/AddEditClients");
        }
        clientService.deleteClient(findClient);

        return modelAndView;
    }

/*    @GetMapping(value = {"/clients/{clientId}/edit"})
    public ModelAndView showEditContact( @PathVariable Long contactId) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Client> optionalClient = clientService.findById(contactId);

        if(!optionalClient.isPresent()) {
            modelAndView.addObject("confirmationMessage","Client not found ");
        } else {
            modelAndView.addObject("client",optionalClient.get());

        }
        modelAndView.setViewName("Client/Edit-Clients");
        return modelAndView;
    }
    @PostMapping(value = {"/clients/{clientId}/edit"})
    public String (@PathVariable Long clientID, @ModelAttribute("client") Client client, Model model){
        try{
            client.setId(clientID);
            clientService.updateClient(client);
            return "redirect:/clients/" + String.valueOf(client.getId());
        } catch (Exception exception){
            String errorMessage  = exception.getMessage();
            model.addAttribute("errorMessage","errorMessage");
            return "client-edit";
        }

    }*/

}
