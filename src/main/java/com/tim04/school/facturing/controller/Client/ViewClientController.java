package com.tim04.school.facturing.controller.Client;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/view-clients")
public class ViewClientController {


    @RequestMapping(method = RequestMethod.GET)
    public String Clients(HttpServletRequest request, Model model) {

        String name = request.getParameter("name");
        return "Clients/Clients";
    }


}
