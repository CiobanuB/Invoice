package com.tim04.school.facturing.controller.InvoiceRecords;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.Invoice;
import com.tim04.school.facturing.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("Invoice-records")
public class InvoiceRecordsController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping()
    public ModelAndView getInvoices() {
        ModelAndView modelAndView = new ModelAndView();
        Optional<List<Invoice>> optionalList = invoiceService.getAllInvoices();
        if (optionalList.isPresent()) {
            modelAndView.addObject("invoiceList", optionalList.get());
        }
        modelAndView.setViewName("Assesments/InvoiceRecords");
        return modelAndView;

    }
}
