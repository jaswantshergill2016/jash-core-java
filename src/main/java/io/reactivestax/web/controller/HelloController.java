package io.reactivestax.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;


@Controller
@RequestMapping("/home")
public class HelloController {

    @RequestMapping
    public String handleRequest (Model model) {
        model.addAttribute("msg", "Sending the current date time");
        model.addAttribute("time", LocalTime.now());
        return "thymeleaf/my-page";
    }
}
