package com.tim04.school.facturing.controller.token;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("expired-token")
public class ExpiredToken {

    @GetMapping()
    public ModelAndView recoverPwd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Signing/Expired-token");
        return modelAndView;
    }

}
