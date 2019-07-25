package com.bank;


import com.bank.domain.User;
import com.bank.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("hit the /register endpoint , going to register.jsp");
        ModelAndView mav = new ModelAndView("register");
        new ModelAndView();
        mav.addObject("user", new User());

        return mav;
    }

    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("user") User user) {
        System.out.println("=========In registerProcess===========");
        if(StringUtils.isEmpty(user.getPassword())){
            ModelAndView mav = new ModelAndView("register");
            System.out.println("got empty password returning the user back to register");
            mav.addObject("emptyPasswordMessage", "password cannot be empty");
            return mav;
        }

        registerService.register(user);
        System.out.println("hit the /registerProcess endpoint , going to welcome.jsp");

        return new ModelAndView("welcome", "firstname", user.getFirstname());
    }


}
