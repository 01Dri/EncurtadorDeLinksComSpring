package me.dri.EncurtadorLinks.unittest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.dri.EncurtadorLinks.exceptions.NotFoundUrl;
import me.dri.EncurtadorLinks.exceptions.UrlFormatInvalid;
import me.dri.EncurtadorLinks.exceptions.UrlShortenerExpired;
import me.dri.EncurtadorLinks.models.UrlEntity;
import me.dri.EncurtadorLinks.models.dto.UrlRequestDTO;
import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import me.dri.EncurtadorLinks.services.UrlServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShortenerServicesTest {

    @Mock
    UrlEntityRepository urlEntityRepository;

    @InjectMocks
    UrlServices urlServices;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGenerateShortenerUrl() throws JsonProcessingException {
        UrlRequestDTO urlRequestDTO = new UrlRequestDTO("https://www.twitch.tv/gordox");
        Instant now = Instant.now();
        now.atZone(ZoneOffset.of("-03:00"));
       UrlEntity urlEntity = new UrlEntity(1L, urlRequestDTO.urlBase(), "urlEncurtada", now, this.urlServices.generateExpirationDate(now), false);
       when(this.urlEntityRepository.save(any())).thenReturn(urlEntity);
       var result = this.urlServices.getUrlEntityShortener(urlRequestDTO);
       assertNotNull(result);
       verify(this.urlEntityRepository).save(any());
    }
    @Test
    void testExceptionUrlFormatInvalid()  {
        UrlRequestDTO urlRequestDTO = new UrlRequestDTO("teste");
        var execption = assertThrows(UrlFormatInvalid.class, () -> this.urlServices.getUrlEntityShortener(urlRequestDTO));
        assertEquals("Url informado é inválido!", execption.getMessage());
    }

    @Test
    void testRedirect() {
        String shortKey = "teste";
        String urlBaseMock = "https://www.twitch.tv/shy11_";
        Instant now = Instant.now();
        now.atZone(ZoneId.of("America/Sao_Paulo"));
        UrlEntity urlEntity = new UrlEntity(1L, urlBaseMock, shortKey, now, this.urlServices.generateExpirationDate(now), false);
        when(this.urlEntityRepository.findByUrlShortenerEntity(shortKey)).thenReturn(urlEntity);
        var urlBase = this.urlServices.redirect(shortKey);
        assertNotNull(urlBase);
        assertEquals(urlBaseMock, urlBase);

    }

    @Test
    void testExceptionUrlNotFound() {
        String urlInvalid = "caguei-nas-calças";
        assertThrows(NotFoundUrl.class, () -> this.urlServices.redirect(urlInvalid));

    }

    @Test
    void testUrlShortenerExpired() {
        UrlRequestDTO urlRequestDTO = new UrlRequestDTO("https://www.twitch.tv/gordox");
        UrlEntity url = new UrlEntity();
        url.setExpiredDate(Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).minusHours(5).toInstant());
        when(this.urlEntityRepository.findAllUrlBase()).thenReturn(List.of(urlRequestDTO.urlBase()));
        when(this.urlEntityRepository.findByUrlBase(urlRequestDTO.urlBase())).thenReturn(url);
        assertThrows(UrlShortenerExpired.class, () -> this.urlServices.getUrlEntityShortener(urlRequestDTO));

    }




}
