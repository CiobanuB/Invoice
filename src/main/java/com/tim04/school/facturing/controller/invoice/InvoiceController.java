package com.tim04.school.facturing.controller.invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.invoice.Invoice;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.client.ClientService;
import com.tim04.school.facturing.service.invoice.InvoiceService;
import com.tim04.school.facturing.service.supplier.SupplierService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("Invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SupplierService supplierService;

    @InitBinder
    public void initBinder ( WebDataBinder binder )
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @GetMapping
    public ModelAndView invoice() {
        ModelAndView modelAndView = new ModelAndView();
        Supplier findSupplier = supplierService.getTheSupplier();
        User user = userService.findLogged();
        //List<Invoice> invoiceList = invoiceService.distinctInvoices();
        List<Invoice> invoiceList = invoiceService.getListInvoice();

        if (findSupplier == null) {
            modelAndView.addObject("clientsList", new ArrayList<>());
            modelAndView.addObject("confirmationMessage", "Please complete supplier profile before adding invoices !");
        } else {
            List<Client> listClients = findSupplier.getClients();
            modelAndView.addObject("clientList", listClients);
        }
        modelAndView.setViewName("Invoice/Invoice");
        modelAndView.addObject("user", user);
        modelAndView.addObject("invoice", new Invoice());
        modelAndView.addObject("invoiceList", invoiceList);

        return modelAndView;
    }


    @PostMapping(value = "/addPath")
    public ModelAndView savePath(@ModelAttribute("User") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("pathValidation", "Invalid folder path");
            modelAndView.setViewName("Invoice/Invoice");
        } else {
            invoiceService.savePathUser(user);
            redirectAttributes.addFlashAttribute("pathValidation", "Default path has been saved !");
            modelAndView.setViewName("redirect:/Invoice");
        }
        /*invoiceService.generateReport(path,invoice,clientName);*/
        //modelAndView.addObject("defaultPath", path);
        return modelAndView;
    }

    @PostMapping(value = "/addInvoice")
    public ModelAndView savePath(@ModelAttribute("invoice") Invoice invoice, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findLogged();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("confirmationMessage", "We could not add the invoice");
            modelAndView.setViewName("Invoice/Invoice");
        } else {
            invoiceService.save(invoice);
            redirectAttributes.addAttribute("invoice", new Invoice());
            redirectAttributes.addAttribute("user", user);
            redirectAttributes.addFlashAttribute("confirmationMessage", "Invoice has been added!");
            modelAndView.setViewName("redirect:/Invoice");
        }
        return modelAndView;
    }

    @DeleteMapping(value = {"/{id}/delete"})
    public ModelAndView deleteClient(@PathVariable Long id, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Client> optionalClient = clientService.findById(id);
        Client getClient = optionalClient.get();
        System.out.println(getClient);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("confirmationMessage", "We could not delete the selected invoice !");
            modelAndView.setViewName("Clients/Invoice");
        } else {
            clientService.deleteClient(getClient);
            redirectAttributes.addFlashAttribute("confirmationMessage", "The invoice has been deleted !");
            modelAndView.setViewName("redirect:/clients");
        }
        return modelAndView;
    }

    @RequestMapping("/{id}/delete")
    public String deleteInvoice(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Invoice> optionalInvoice = invoiceService.findById(id);
        Invoice invoice = optionalInvoice.get();
        redirectAttributes.addFlashAttribute("confirmationMessage", "The invoice has been deleted ");
        invoiceService.deleteInvoice(invoice);
        return "redirect:/Invoice";
    }

    @RequestMapping("/{id}/generateInvoice")
    public String generateInvoice(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Invoice> optionalInvoice = invoiceService.findById(id);
        Invoice invoice = optionalInvoice.get();
        redirectAttributes.addFlashAttribute("confirmationMessage", "The invoice has been generate on your computer");
        invoiceService.generateReport(invoice);
        return "redirect:/Invoice";
    }


    @RequestMapping("/{id}/sendMailInvoice")
    public String sendMailInvoice(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Invoice> optionalInvoice = invoiceService.findById(id);
        Invoice invoice = optionalInvoice.get();
        redirectAttributes.addFlashAttribute("confirmationMessage", "The invoice has been sent to " + invoice.getClientName());
        invoiceService.generateReport(invoice);
        return "redirect:/Invoice";
    }

    @PostMapping("/{id}/sendMailInvoice")
    public ModelAndView sendOrEditInvoice(@PathVariable Long id, @ModelAttribute Invoice invoice, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Invoice/send-invoice");
        } else {
            invoiceService.updateInvoice(invoice);
            redirectAttributes.addFlashAttribute("confirmationMessage", "The client has been updated !");
            modelAndView.setViewName("redirect:/clients");
        }
        return modelAndView;
    }



}