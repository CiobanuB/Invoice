package com.tim04.school.facturing.controller.Invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.Invoice;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.client.ClientService;
import com.tim04.school.facturing.service.invoice.InvoiceService;
import com.tim04.school.facturing.service.supplier.SupplierService;
import com.tim04.school.facturing.user.UserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ModelAndView invoice() {
        ModelAndView modelAndView = new ModelAndView();
        Supplier findSupplier = supplierService.getTheSupplier();
        User user = findSupplier.getUser();
        List<Client> listClients = findSupplier.getClients();
        List<Invoice> invoiceList = invoiceService.distinctInvoices();

        modelAndView.setViewName("Invoice/Invoice");
        modelAndView.addObject("user", user);
        modelAndView.addObject("invoice", new Invoice());
        modelAndView.addObject("invoiceList", invoiceList);
        modelAndView.addObject("clientList", listClients);

        return modelAndView;
    }


    @PostMapping(value = "/addPath")
    public ModelAndView savePath(@ModelAttribute("User") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){
            modelAndView.addObject("pathValidation","Invalid folder path");
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

        if(bindingResult.hasErrors()){
            modelAndView.addObject("confirmationMessage","We could not add the invoice");
            modelAndView.setViewName("Invoice/Invoice");
        } else {
            invoiceService.save(invoice);
            redirectAttributes.addAttribute("invoice", new Invoice());
            redirectAttributes.addFlashAttribute("confirmationMessage", "Invoice has been added!");
            modelAndView.setViewName("redirect:/Invoice");
        }
        //invoiceService.generateReport(path,invoice,clientName);
        //modelAndView.addObject("defaultPath", path);
        return modelAndView;
    }

}