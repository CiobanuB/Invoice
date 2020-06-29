package com.tim04.school.facturing.controller.controllerAdvice;

import com.tim04.school.facturing.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @Autowired
    private InvoiceService invoiceService;

    @ModelAttribute
    public void addNumberInvoicestoModel(Model model){
        Integer numberInvoiced = invoiceService.numberInvoiced();
        Integer numberClients = invoiceService.clientNumbers();
        if( numberClients != null && numberClients != null){
            model.addAttribute("numberInvoiced",numberInvoiced);
            model.addAttribute("clientNumbers",numberClients);
        } else {
            model.addAttribute("clientNumbers","0");
            model.addAttribute("numberInvoiced","0");
        }

    }
}
