package com.tim04.school.facturing.controller.Client;

import com.tim04.school.facturing.Exceptions.RecordNotFoundException;
import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView editClient(@PathVariable Long clientId, @ModelAttribute("client") Client client, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            client.setId(clientId);
            clientService.updateClient(client);
            redirectAttributes.addFlashAttribute("confirmationMessage", "The client has been updated !");
            modelAndView.setViewName("redirect:/clients");
        } catch (Exception exception) {
            String errorMessage = exception.getMessage();
            modelAndView.addObject("errorMessage", "We could not update !");
            modelAndView.setViewName("Clients/EditClients");
        }
        return modelAndView;
    }


 /*   @PostMapping(value = {"/clients/{clientId}/edit"})
    public String updateContact(Model model,
                                @PathVariable Long contactId,
                                @ModelAttribute("client") Client client) {
        try {
            client.setId(contactId);
            clientService.updateClient(client);
            return "redirect:/clients/" + String.valueOf(client.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);

            return "Clients/EditClients";
        }
    }*/
 /*   @PostMapping(value = {"/clients/{clientId}/edit"})
    public ModelAndView editClient(@PathVariable Long clientId, @ModelAttribute("client") Client client, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Client findClient = clientService.findClient(client);
        if (findClient != null) {
            bindingResult.rejectValue("name", "error.client", "There is another client registered with " + findClient.getName() + " name");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Clients/EditClients");
            //modelAndView.setViewName("redirect:/clients");
        } else {
            client.setId(clientId);
            clientService.updateClient(client);
            redirectAttributes.addFlashAttribute("confirmationMessage", "Client " + client.getName() + " has been modified !");
            modelAndView.setViewName("redirect:/clients");
        }
        return modelAndView;
    }*/
    /*@RequestMapping(path = "/clients/delete/{clientId}")
    public ModelAndView deleteEmployeeById(Model model, @PathVariable("clientId") Long clientId, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        try {
            clientService.deleteById(clientId);
            return "redirect:/clients";
        } catch (RecordNotFoundException ex) {
            //modelAndView.addObject("errorMessageModal","Client not found");
            redirectAttributes.addFlashAttribute("confirmationMessage", "Client not found");

        }
    }*/
    /*@RequestMapping(path = "/clients/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        service.deleteEmployeeById(id);
        return "redirect:/";
    }*/
}

