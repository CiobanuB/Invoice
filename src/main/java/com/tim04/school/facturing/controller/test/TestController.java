package com.tim04.school.facturing.controller.test;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    private ClientService clientService;

    @GetMapping("getClient")
    public ModelAndView getClient() {
        Client theClient = new Client();
        theClient = clientService.getClient();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("getClient.html");
        modelAndView.addObject("client", theClient);

        return modelAndView;

    }

    @GetMapping("getAll")
    public ModelAndView getAll() {
        List<Client> allClients = clientService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("getAll.html");
        modelAndView.addObject("clients", allClients);

        return modelAndView;

    }
}