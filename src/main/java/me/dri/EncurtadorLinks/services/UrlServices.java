package me.dri.EncurtadorLinks.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import me.dri.EncurtadorLinks.exceptions.NotFoundUrl;
import me.dri.EncurtadorLinks.exceptions.UrlFormatInvalid;
import me.dri.EncurtadorLinks.exceptions.UrlShortenerExpired;
import me.dri.EncurtadorLinks.models.UrlEntity;
import me.dri.EncurtadorLinks.models.dto.UrlEntityDTO;
import me.dri.EncurtadorLinks.models.dto.UrlRequestDTO;
import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UrlServices {
    @Autowired
    private UrlEntityRepository repository;

    private static final String ZONEID = "America/Sao_Paulo";


    public UrlEntityDTO getUrlEntityShortener(UrlRequestDTO url) throws JsonProcessingException {

        if (!url.urlBase().contains("https://")) {
            throw new UrlFormatInvalid("Url informado é inválido!");
        }
        var urlEntity = this.verifyIfUrlEntityAlreadyExists(url);
        if (!(urlEntity == null)) {
            return this.convertEntityToDTO(urlEntity);
        }


        String shortKey = this.generateUrlShortener();
        LocalDateTime nowDateTime = LocalDateTime.now();
        urlEntity = new UrlEntity(null, url.urlBase(), shortKey, nowDateTime, this.generateExpirationDate(nowDateTime), false);
        System.out.println(urlEntity.getDateCreatedUrlShortener());
        this.repository.save(urlEntity);

        return this.convertEntityToDTO(urlEntity);

    }


    public String redirect(String shortKey) {
        UrlEntity urlEntityByShortener = this.repository.findByUrlShortenerEntity(shortKey);
        if (urlEntityByShortener == null) {
            throw new NotFoundUrl("Não foi possivel encontrar a URL");
        }
        if (verifyExpiredDateUrlShortener(urlEntityByShortener.getExpiredDate())) {
            urlEntityByShortener.setExpired(true);
            throw new UrlShortenerExpired("Url expirado!");
        }

        return urlEntityByShortener.getUrlBase();
    }


    public LocalDateTime generateExpirationDate(LocalDateTime dataCreated) {
        return dataCreated.plusHours(2);
    }

    private String generateUrlShortener() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", "");
        return uuidString.substring(0, 7);
    }

    private Boolean verifyExpiredDateUrlShortener(LocalDateTime expiredDate) {
        LocalDateTime date = LocalDateTime.now();
        return date.isAfter(expiredDate);
    }

    private UrlEntityDTO convertEntityToDTO(UrlEntity urlEntity) {
        return new UrlEntityDTO(urlEntity.getId(), urlEntity.getUrlBase(),
                urlEntity.getUrlShortener(), urlEntity.getDateCreatedUrlShortener(),
                urlEntity.getExpiredDate(), urlEntity.getExpired());
    }

    public UrlEntity verifyIfUrlEntityAlreadyExists(UrlRequestDTO url) {
        List<String> urlsBaseInDatabase = this.repository.findAllUrlBase();
        if (urlsBaseInDatabase.contains(url.urlBase())) {
            UrlEntity urlEntity = this.repository.findByUrlBase(url.urlBase());
            if (this.verifyExpiredDateUrlShortener(urlEntity.getExpiredDate())) {
                LocalDateTime nowDateTime = LocalDateTime.now();
                urlEntity.setUrlShortener(this.generateUrlShortener());
                urlEntity.setDateCreatedUrlShortener(nowDateTime);
                urlEntity.setExpiredDate(this.generateExpirationDate(nowDateTime));
                urlEntity.setExpired(false);
                this.repository.save(urlEntity);
                return urlEntity;
            }
            return urlEntity;
        }
        return null;
    }

}
