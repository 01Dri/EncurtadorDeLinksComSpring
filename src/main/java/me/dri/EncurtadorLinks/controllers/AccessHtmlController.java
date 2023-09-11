package me.dri.EncurtadorLinks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AccessHtmlController {

    @GetMapping("/page")
    public String htmlPageMain() {
        return "index.html";
    }
    
}
