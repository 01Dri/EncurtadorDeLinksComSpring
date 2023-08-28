package me.dri.EncurtadorLinks.services;


import me.dri.EncurtadorLinks.exceptions.urlFormatInvalid;
import me.dri.EncurtadorLinks.exceptions.urlIsEmpty;
import org.springframework.stereotype.Service;

@Service
public class UrlServices {


    public String getShortenerUrl(String url) {
        if (!url.contains("https:") || !url.contains("http:")) {
            throw new urlFormatInvalid("Url informada é inválida!");
        }

        if (url.isBlank()) {
            throw  new urlIsEmpty("Por favor forneça uma URL");
        }

        return "beleza";

    }
}
