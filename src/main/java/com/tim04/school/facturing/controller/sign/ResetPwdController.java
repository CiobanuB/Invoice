package com.tim04.school.facturing.controller.sign;

import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.tokenService.ResetTokenService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("reset-password")
public class ResetPwdController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResetTokenService resetTokenService;

/*    @GetMapping()
    public String recoverPwd(@RequestParam(value = "mail") String mail,Model model) {
     ModelAndView modelAndView = new ModelAndView();
       //modelAndView.addAttribute("user", user);

        model.addAttribute();
        modelAndView.setViewName("Signing/Recover-password");
        return modelAndView;
    }*/
    @GetMapping()
    public ModelAndView recoverPwd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("Signing/Recover-password");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView recoverPassword(User user , BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView();
        User findUser = userService.findUserByEmail(user.getMail());

        if (findUser == null) {
            bindingResult.rejectValue("mail","error.mail",  "We didn't find an account with " + user.getMail() + " adress" );
        }

       if (bindingResult.hasErrors()) {
            //redirectAttrs.addFlashAttribute("validation", "We didn't find an account with " + user.getMail() + " adress");
            modelAndView.setViewName("Signing/Recover-password");
        } else {
            resetTokenService.createResetPwdRequest(user);
            redirectAttrs.addFlashAttribute("validation", "A password link reset has been sent to : " + user.getMail());
            modelAndView.setViewName("redirect:/reset-password");
        }
        return modelAndView;
    }


}