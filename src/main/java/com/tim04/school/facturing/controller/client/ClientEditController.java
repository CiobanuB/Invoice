package com.tim04.school.facturing.controller.client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ClientEditController {
    @Autowired
    ClientService clientService;

    @GetMapping(value = {"/clients/{id}/edit"})
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
    }

    @PostMapping(value = {"/clients/{clientId}/edit"})
    public ModelAndView editClient(@PathVariable Long clientId, @ModelAttribute("client") Client client, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Clients/EditClients");
        } else {
            client.setId(clientId);
            clientService.updateClient(client);
            redirectAttributes.addFlashAttribute("confirmationMessage", "The client has been updated !");
            modelAndView.setViewName("redirect:/clients");
        }

        return modelAndView;
    }

    @DeleteMapping(value = {"/clients/{id}/delete"})
        public ModelAndView deleteClient(@PathVariable Long id, BindingResult bindingResult, RedirectAttributes redirectAttributes){
            ModelAndView modelAndView = new ModelAndView();
            Optional<Client> optionalClient = clientService.findById(id);
            Client getClient = optionalClient.get();
            if (bindingResult.hasErrors()) {
                modelAndView.addObject("confirmationMessage", "We could not delete the selected client !");
                modelAndView.setViewName("Clients/AddEditClients");
            } else {
                clientService.deleteClient(getClient);
                redirectAttributes.addFlashAttribute("confirmationMessage", "The client has been deleted !");
                modelAndView.setViewName("redirect:/clients");
            }
            return modelAndView;
        }
    /*@RequestMapping(value = {"/clients/{id}/delete"})
    public ModelAndView deleteProduct(@PathVariable Long id,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Client> optionalClient = clientService.findById(id);
        Client getClient = optionalClient.get();
        if(!optionalClient.isPresent())
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("confirmationMessage", "We could not delete the selected client !");
            modelAndView.setViewName("Clients/AddEditClients");
        } else {
            clientService.deleteClient(getClient);
            redirectAttributes.addFlashAttribute("confirmationMessage", "The client has been updated !");
            modelAndView.setViewName("redirect:/clients");
        }
        return modelAndView;
    }*/
/*    @RequestMapping("/clients/{id}/delete")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        Optional<Client> optionalClient = clientService.findById(id);
        Client getClient = optionalClient.get();
        clientService.deleteClient(getClient);
        return "redirect:/clients";
    }*/
}

