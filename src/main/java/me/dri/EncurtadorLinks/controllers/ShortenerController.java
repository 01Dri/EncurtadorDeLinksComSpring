package me.dri.EncurtadorLinks.controllers;



import com.fasterxml.jackson.core.JsonProcessingException;
import me.dri.EncurtadorLinks.models.dto.UrlRequestDTO;
import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import me.dri.EncurtadorLinks.services.UrlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/encurtador")
public class ShortenerController {

    @Autowired
    private UrlServices urlServices;

    @Autowired
    private UrlEntityRepository repository;

    @PostMapping("/encurtar")
    public ResponseEntity shortenerUrl(@RequestBody UrlRequestDTO url) throws JsonProcessingException {
        URI location = URI.create("http:localhost/8080");
        return ResponseEntity.created(location).body(this.urlServices.getUrlEntityShortener(url));
    }


    @GetMapping("/all")
    public ResponseEntity findALL() {
        return ResponseEntity.ok().body(this.repository.findAllUrl());
    }


}
