package com.group6.cleansweep.controller;

import com.group6.cleansweep.models.User;
import com.group6.cleansweep.models.UserDB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.group6.cleansweep.models.api;

import java.io.IOException;

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

        UserDB db = UserDB.getInstance();

        if(!db.exists(user)){
            return "register";
        }
        user = db.getUser(user);
        model.addAttribute("user", user);

        return "home";
    }


//    @PostMapping("/run")
//    public String csRun(@ModelAttribute User user, Model model) throws IOException, InterruptedException {
//        model.addAttribute("user", user);
////        user.cleansweep.run();
//        return "home";
//    }

    @GetMapping("/register")
    public String signup(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String registerResults(User u){

        UserDB db = UserDB.getInstance();
        if(db.exists(u)){
            return "failedRegister";
        }

        db.addToDB(u);

        return "home";
    }

}//
