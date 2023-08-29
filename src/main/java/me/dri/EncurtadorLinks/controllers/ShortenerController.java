package me.dri.EncurtadorLinks.controllers;



import me.dri.EncurtadorLinks.services.UrlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dri/encurtador")
public class ShortenerController {

    @Autowired
    private UrlServices urlServices;

    @PostMapping("/encurtar")
    public ResponseEntity shortenerUrl(@RequestBody String url) {
        return ResponseEntity.ok().body(this.urlServices.getUrlEntityShortener(url));
    }

}
