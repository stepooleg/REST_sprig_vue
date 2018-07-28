package com.example.restApp.controller;

import com.example.restApp.models.User;
import com.example.restApp.repository.MsgRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MsgRepo msgRepo;

    @Autowired
    public MainController(MsgRepo msgRepo) {
        this.msgRepo = msgRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile",user);
        data.put("messages",msgRepo.findAll());

        model.addAttribute("frontendData", data);

        return "index";
    }
}
