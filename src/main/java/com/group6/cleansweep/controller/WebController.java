package com.group6.cleansweep.controller;

import com.group6.cleansweep.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.group6.cleansweep.models.api;

@Controller
public class WebController {

    @RequestMapping("/api")
    public api ApiExample(@RequestParam(value = "para",
            defaultValue = "Robot") String api) {
        api response = new api();
        response.setId(1);
        response.setMessage("Some api "+ api);
        return response;
    }

    @GetMapping("/login")
    public String user(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String userSubmit(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);
        return "home";
    }

}//
