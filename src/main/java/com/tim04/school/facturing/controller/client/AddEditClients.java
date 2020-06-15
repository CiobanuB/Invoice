package com.tim04.school.facturing.controller.client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.service.client.ClientService;
import com.tim04.school.facturing.service.supplier.SupplierService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping()
    public ModelAndView addClient(@ModelAttribute(value = "client") Client client, BindingResult bindingResult, RedirectAttributes redirectAttrs)  {
        ModelAndView modelAndView = new ModelAndView();
        Supplier supplier = supplierService.getTheSupplier();
        Optional<Client> optionalClientName = clientService.findClientByName(client.getName());
        Optional<Client> optionalClientCif = clientService.findClientByCif(client.getCif());
        if (optionalClientName.isPresent() || supplier == null || optionalClientCif.isPresent()) {
            bindingResult.rejectValue("name", "error.client", "There is another client registered with " + client.getName() + " name");
        }
        if (bindingResult.hasErrors()) {
            if(supplier == null) {
                redirectAttrs.addFlashAttribute("confirmationMessage", "Please complete your supplier section before adding clients.");
            } else if (optionalClientCif.isPresent()) {
                redirectAttrs.addFlashAttribute("confirmationMessage", "There is another clients with this CIF");
            }
            else {
                redirectAttrs.addFlashAttribute("confirmationMessage", "There is another client with " + client.getName() + " name !");
            }
            modelAndView.setViewName("redirect:/clients");
        } else {
            clientService.addClient(client);
            modelAndView.addObject("client", new Client());
            redirectAttrs.addFlashAttribute("confirmationMessage", "Client " + client.getName() + " has been created succesfully !");
            redirectAttrs.addAttribute("client", new Client());
            modelAndView.setViewName("redirect:/clients");
        }
        return modelAndView;
    }


}
