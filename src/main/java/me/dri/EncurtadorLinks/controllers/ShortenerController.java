package me.dri.EncurtadorLinks.controllers;



import com.fasterxml.jackson.core.JsonProcessingException;
import me.dri.EncurtadorLinks.models.dto.UrlRequestDTO;
import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import me.dri.EncurtadorLinks.services.UrlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/dri/encurtador")
public class ShortenerController {

    @Autowired
    private UrlServices urlServices;

    @Autowired
    private UrlEntityRepository repository;

    @PostMapping("/encurtar")
    public ResponseEntity shortenerUrl(@RequestBody UrlRequestDTO url) throws JsonProcessingException {
        return ResponseEntity.ok().body(this.urlServices.getUrlEntityShortener(url));
    }
    @GetMapping("/acessar/{shortKey}")
    public RedirectView acessSUrlBaseByShortener(@PathVariable String shortKey) {
        var urlBase = this.urlServices.redirect(shortKey);
        var urlReplaced = urlBase.replace("url:", "");
       System.out.println(urlReplaced);
       return new RedirectView(urlBase);
   }

    @GetMapping("/all")
    public ResponseEntity findALL() {
        return ResponseEntity.ok().body(this.repository.findAllUrl());
    }


}
