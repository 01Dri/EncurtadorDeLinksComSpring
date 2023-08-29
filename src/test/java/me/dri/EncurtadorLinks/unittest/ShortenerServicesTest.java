package me.dri.EncurtadorLinks.unittest;


import me.dri.EncurtadorLinks.exceptions.NotFoundUrl;
import me.dri.EncurtadorLinks.exceptions.UrlFormatInvalid;
import me.dri.EncurtadorLinks.exceptions.UrlShortenerExpired;
import me.dri.EncurtadorLinks.models.UrlEntity;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    void testGenerateShortenerUrl()  {
        String urlMock = "https://twitter.com/home";
        Instant now = Instant.now();
        now.atZone(ZoneOffset.of("-03:00"));
        UrlEntity urlEntity = new UrlEntity(1L, urlMock, "urlEncurtada", now, this.urlServices.generateExpirationDate(now), false);
        when(this.urlEntityRepository.save(any())).thenReturn(urlEntity);
        var result = this.urlServices.getUrlEntityShortener(urlMock);
        assertNotNull(result);
        verify(this.urlEntityRepository).save(any());
    }
    @Test
    void testExceptionUrlFormatInvalid()  {
        String urlMock = "url-invalido";
        var execption = assertThrows(UrlFormatInvalid.class, () -> this.urlServices.getUrlEntityShortener(urlMock));
        assertEquals("Url informado é inválido!", execption.getMessage());
    }

    @Test
    void testRedirect() {
        String shortKey = "teste";
        String urlBaseMock = "https://www.twitch.tv/shy11_";
        when(this.urlEntityRepository.findUrlBase(any())).thenReturn(urlBaseMock);
        var urlBaseResult = this.urlServices.redirect(shortKey);
        assertEquals(urlBaseMock, urlBaseResult);

    }

    @Test
    void testExceptionUrlNotFound() {
        String urlInvalid = "caguei-nas-calças";
        assertThrows(NotFoundUrl.class, () -> this.urlServices.redirect(urlInvalid));

    }

    @Test
    void testUrlShortenerExpired() {
        String urlMock = "https://twitter.com/home";
        UrlEntity url = new UrlEntity();
        url.setExpiredDate(Instant.now().atZone(ZoneOffset.of("-03:00")).plusHours(1).toInstant());
        when(this.urlEntityRepository.findAllUrlBase()).thenReturn(List.of(urlMock));
        when(this.urlEntityRepository.findByUrlBase(any())).thenReturn(url);
        assertThrows(UrlShortenerExpired.class, () -> this.urlServices.getUrlEntityShortener(urlMock));

    }

}
