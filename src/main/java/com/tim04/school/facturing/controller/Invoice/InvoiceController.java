package com.tim04.school.facturing.controller.Invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.Invoice;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.client.ClientService;
import com.tim04.school.facturing.service.invoice.InvoiceService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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


    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ModelAndView invoice() throws FileNotFoundException, JRException {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findLogged();
        modelAndView.addObject("user", user);
        List<Invoice> invoiceList = invoiceService.getListInvoice();
        modelAndView.addObject("invoiceList",invoiceList);
        modelAndView.setViewName("Invoice/Invoice.html");
        return modelAndView;
    }



/*
    @GetMapping()
    public String generateFolder(@RequestParam("defaultPath") String path) {
        return invoiceService.generateClientFolder();
    }
*/


    @PostMapping()
    public ModelAndView savePath(@RequestParam(value = "defaultPath") String path, @ModelAttribute("invoice") Invoice invoice,@RequestParam(value = "clientName") String clientName) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(path);
        User user = userService.findLogged();
    //    invoiceService.generateClientFolder(path);
        user.setDefaultPath(path);
        userService.updateUser(user);
        invoiceService.generateReport(path,invoice,clientName);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("Invoice/Invoice");
        return modelAndView;
    }

}