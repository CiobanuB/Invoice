package com.tim04.school.facturing.controller.invoice;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.Invoice;
import com.tim04.school.facturing.service.invoice.InvoiceService;
import com.tim04.school.facturing.user.tokenService.MailPropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class SendInvoiceMail {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private MailPropService mailPropService;

    @GetMapping(value = {"/Invoice/{id}/Send-invoice"})
    public ModelAndView showInvoice(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Invoice> findInvoice = invoiceService.findById(id);
        Invoice invoice = findInvoice.get();

        if (!findInvoice.isPresent()) {
            modelAndView.addObject("confirmationMessage", "We did not find the required invoice ");
            modelAndView.addObject("invoice", new Invoice());
        } else {
            modelAndView.addObject("invoice", invoice);
        }
        modelAndView.setViewName("invoice/send-invoice");
        return modelAndView;
    }

    @PostMapping("/Invoice/{id}/Send-invoice")
    public ModelAndView sendInvoiceMail(@ModelAttribute Invoice invoice, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        if (invoice == null) {
            modelAndView.addObject("confirmationMessage", "We did not find any invoice");
            modelAndView.setViewName("invoice/send-invoice");
        } else {
            mailPropService.sendInvoiceMail(invoice);
            redirectAttributes.addFlashAttribute("confirmationMessage", "The mail has been sent");
            modelAndView.setViewName("redirect:/Invoice");
        }

        return modelAndView;

    }
}
