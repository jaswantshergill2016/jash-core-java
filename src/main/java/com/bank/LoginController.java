package com.bank;


import com.bank.domain.Login;
import com.bank.domain.User;
import com.bank.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

   @Autowired
   LoginService loginService;
/*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("hit the /login endpoint , going to login.jsp");
        ModelAndView mav = new ModelAndView("login");
        new ModelAndView();
        mav.addObject("login", new Login());

        return mav;
    }
    */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("hit the /login endpoint , going to login.jsp");
        ModelAndView mav = new ModelAndView("login");
        new ModelAndView();
        mav.addObject("login", new Login());

        return mav;
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
                                     @ModelAttribute("login") Login login) {
        ModelAndView mav = null;
        System.out.println("hit the /loginProcess endpoint with values " + login.toString());

        User user = loginService.validateUser(login);

        System.out.println("====user==========>  "+ user);
        if (null != user) {
        //if(login.getUsername().equals("jaswant")){
            mav = new ModelAndView("welcome");
            System.out.println("going to welcome.jsp");
            System.out.println("login.getUsername()  "+user.getUsername());
            mav.addObject("username", user.getUsername());
        } else {
            mav = new ModelAndView("error");
            System.out.println("going to error.jsp");
            mav.addObject("message", "Username or Password is wrong!!");
        }

        return mav;
    }

}
