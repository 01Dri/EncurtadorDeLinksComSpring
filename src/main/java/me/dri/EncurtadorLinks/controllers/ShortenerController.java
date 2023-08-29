package me.dri.EncurtadorLinks.controllers;



import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import me.dri.EncurtadorLinks.services.UrlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dri/encurtador")
public class ShortenerController {

    @Autowired
    private UrlServices urlServices;

    @Autowired
    private UrlEntityRepository repository;

    @PostMapping("/encurtar")
    public ResponseEntity shortenerUrl(@RequestBody String url) {
        return ResponseEntity.ok().body(this.urlServices.getUrlEntityShortener(url));
    }
    @PostMapping("/acessar")
    public ResponseEntity acessSUrlBaseByShortener(@RequestBody String shortKey) {
        var urlBase = this.urlServices.redirect(shortKey);
        System.out.println(urlBase);
        return ResponseEntity.ok().body(urlBase);
    }

    @GetMapping("/all")
    public ResponseEntity findALL() {
        return ResponseEntity.ok().body(this.repository.findAllUrl());
    }


}
