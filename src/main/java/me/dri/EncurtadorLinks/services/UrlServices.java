package me.dri.EncurtadorLinks.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.swing.interop.SwingInterOpUtils;
import me.dri.EncurtadorLinks.exceptions.NotFoundUrl;
import me.dri.EncurtadorLinks.exceptions.UrlFormatInvalid;
import me.dri.EncurtadorLinks.exceptions.UrlShortenerExpired;
import me.dri.EncurtadorLinks.models.UrlEntity;
import me.dri.EncurtadorLinks.models.dto.UrlRequestDTO;
import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class UrlServices {
    @Autowired
    private UrlEntityRepository repository;

    private static final String ZONEID = "America/Sao_Paulo";


    public UrlEntity getUrlEntityShortener(UrlRequestDTO url) throws JsonProcessingException {

        if (!url.urlBase().contains("https://")) {
            throw new UrlFormatInvalid("Url informado é inválido!");
        }
        List<String> urlsBaseInDatabase = this.repository.findAllUrlBase();
        if (urlsBaseInDatabase.contains(url)) {
            UrlEntity urlEntity = this.repository.findByUrlBase(url.urlBase());
            if (this.verifyExpiredDateUrlShortener(urlEntity.getExpiredDate())) {
                    urlEntity.setExpired(true);
                    throw  new UrlShortenerExpired("Url expirado!");
                }
            return urlEntity;
            }

        String shortKey = this.generateUrlShortener();
        Instant now = Instant.now();
        now.atZone(ZoneId.of(ZONEID));
        UrlEntity urlEntity = new UrlEntity(null, url.urlBase(), shortKey, now, this.generateExpirationDate(now), false);
        this.repository.save(urlEntity);

        return urlEntity;

    }
        public String redirect(String shortKey) {
         UrlEntity urlEntityByShortener = this.repository.findByUrlShortenerEntity(shortKey);
            if (urlEntityByShortener == null) {
               throw  new NotFoundUrl("Não foi possivel encontrar a URL: ");
          }
            if (verifyExpiredDateUrlShortener(urlEntityByShortener.getExpiredDate())) {
                urlEntityByShortener.setExpired(true);
                throw  new UrlShortenerExpired("Url expirada!");
            }
           return  urlEntityByShortener.getUrlBase();
       }
        public Instant generateExpirationDate (Instant dateCreated) {
                return dateCreated.atZone(ZoneId.of(ZONEID)).plusHours(2).toInstant();
        }
        private String generateUrlShortener() {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString().replace("-", "");
            return uuidString.substring(0, 8);
        }

        private Boolean  verifyExpiredDateUrlShortener(Instant expiredDate) {
            Instant now = Instant.now();
            now.atZone(ZoneId.of(ZONEID));
            return now.isAfter(expiredDate);
        }

    }
