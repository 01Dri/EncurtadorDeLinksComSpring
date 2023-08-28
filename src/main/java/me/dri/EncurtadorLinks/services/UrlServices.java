package me.dri.EncurtadorLinks.services;


import jakarta.persistence.Column;
import me.dri.EncurtadorLinks.exceptions.ErrorWhileGenerateUrlShortener;
import me.dri.EncurtadorLinks.exceptions.NotFoundUrl;
import me.dri.EncurtadorLinks.exceptions.UrlFormatInvalid;
import me.dri.EncurtadorLinks.models.UrlEntity;
import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UrlServices {
    @Autowired
    private UrlEntityRepository repository;

    private static final String ALGORITHM = "MD5";


    public UrlEntity getUrlEntityShortener(String url)  {

        if (!url.startsWith("https://") || url.startsWith("http://") || url.isBlank()) {
            throw new UrlFormatInvalid("Url informado é inválido!");
        }
        List<String> urlsBaseInDatabase = this.repository.findAllUrl();

        if (urlsBaseInDatabase.contains(url)) {
            return this.repository.findByUrlBase(url);
        }
        String shortKey = this.generateUrlShortener();
        UrlEntity urlEntity = new UrlEntity(null, url, shortKey, new Date(), this.generateExpirationDate(), false);
        this.repository.save(urlEntity);

        return urlEntity;

    }
        public Instant generateExpirationDate () {
            return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
        }
        private String generateUrlShortener() {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString().replace("-", "");
            return uuidString.substring(0, 8);
        }

        public UrlEntity redirect(String shortKey) {
            UrlEntity urlEntity = this.repository.findByUrlShortener(shortKey);
            if (urlEntity == null) {
                throw  new NotFoundUrl("Não foi possivel encontrar a URL: ");
            }
            return  urlEntity;
        }
    }
