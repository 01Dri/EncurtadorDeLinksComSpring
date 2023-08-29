package me.dri.EncurtadorLinks.services;
import me.dri.EncurtadorLinks.exceptions.NotFoundUrl;
import me.dri.EncurtadorLinks.exceptions.UrlFormatInvalid;
import me.dri.EncurtadorLinks.exceptions.UrlShortenerExpired;
import me.dri.EncurtadorLinks.models.UrlEntity;
import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class UrlServices {
    @Autowired
    private UrlEntityRepository repository;

    private static final String ALGORITHM = "MD5";


    public UrlEntity getUrlEntityShortener(String url)  {

        if (!url.contains("https://")) {
            throw new UrlFormatInvalid("Url informado é inválido!");
        }
        List<String> urlsBaseInDatabase = this.repository.findAllUrlBase();
        if (urlsBaseInDatabase.contains(url)) {
            UrlEntity urlEntity = this.repository.findByUrlBase(url);
            if (this.verifyExpiredDateUrlShortener(urlEntity.getExpiredDate())) {
                    urlEntity.setExpired(true);
                    throw  new UrlShortenerExpired("Url expirado!");
                }
            }

        String shortKey = this.generateUrlShortener();
        Instant now = Instant.now();
        now.atZone(ZoneOffset.of("-03:00"));
        UrlEntity urlEntity = new UrlEntity(null, url, shortKey, now, this.generateExpirationDate(now), false);
        this.repository.save(urlEntity);

        return urlEntity;

    }
    //  Por algum motivo esse metodo não funciona!
        public String redirect(String shortKey) {
            String urlEntity = this.repository.findUrlBase(shortKey);
            if (urlEntity == null) {
                throw  new NotFoundUrl("Não foi possivel encontrar a URL: ");
            }
            return  urlEntity;
        }
        public Instant generateExpirationDate (Instant dateCreated) {
                return dateCreated.atZone(ZoneOffset.of("-03:00")).plusHours(2).toInstant();
        }
        private String generateUrlShortener() {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString().replace("-", "");
            return uuidString.substring(0, 8);
        }

        private Boolean  verifyExpiredDateUrlShortener(Instant expiredDate) {
            Instant now = Instant.now();
            now.atZone(ZoneOffset.of("-03:00"));
            return now.isBefore(expiredDate);
        }
    }
