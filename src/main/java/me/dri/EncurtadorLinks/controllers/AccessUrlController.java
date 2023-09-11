package me.dri.EncurtadorLinks.controllers;


import me.dri.EncurtadorLinks.services.UrlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AccessUrlController {

    @Autowired
    private UrlServices urlServices;
    @GetMapping("/enc/{shortKey}")
    public RedirectView acessSUrlBaseByShortener(@PathVariable String shortKey) {
        var urlDTO = this.urlServices.redirect(shortKey);
        return new RedirectView(urlDTO);
    }
}
