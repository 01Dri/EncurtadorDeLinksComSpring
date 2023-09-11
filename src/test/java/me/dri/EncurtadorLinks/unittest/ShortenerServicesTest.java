package me.dri.EncurtadorLinks.unittest;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.time.LocalDateTime;
import java.util.Collections;


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
        LocalDateTime nowDateTime = LocalDateTime.now();
       UrlEntity urlEntity = new UrlEntity(1L, urlRequestDTO.urlBase(), "urlEncurtada", nowDateTime, this.urlServices.generateExpirationDate(nowDateTime), false);
       when(this.urlEntityRepository.save(any())).thenReturn(urlEntity);
       var result = this.urlServices.getUrlEntityShortener(urlRequestDTO);
       assertNotNull(result);
       verify(this.urlEntityRepository).save(any());
    }
    @Test
    void testUrlEntityAlreadyExists()  {
        UrlRequestDTO urlRequestDTO = mock(UrlRequestDTO.class);
        when(urlRequestDTO.urlBase()).thenReturn("https://www.twitch.tv/gordox");
        LocalDateTime nowDateTime = LocalDateTime.now();
        UrlEntity urlEntity = new UrlEntity(1L, urlRequestDTO.urlBase(), "urlEncurtada", nowDateTime, this.urlServices.generateExpirationDate(nowDateTime), false);
        when(this.urlEntityRepository.findAllUrlBase()).thenReturn(Collections.singletonList(urlEntity.getUrlBase()));
        when(this.urlEntityRepository.findByUrlBase(urlRequestDTO.urlBase())).thenReturn(urlEntity);
        var result = this.urlServices.verifyIfUrlEntityAlreadyExists(urlRequestDTO);
        System.out.println(result.getUrlBase());
        assertNotNull(result);

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
        LocalDateTime nowDateTime = LocalDateTime.now();
        UrlEntity urlEntity = new UrlEntity(1L, urlBaseMock, shortKey, nowDateTime, this.urlServices.generateExpirationDate(nowDateTime), false);
        when(this.urlEntityRepository.findByUrlShortenerEntity(shortKey)).thenReturn(urlEntity);
        var urlBase = this.urlServices.redirect(shortKey);
        assertNotNull(urlBase);
        assertEquals(urlBaseMock, urlBase);

    }

    @Test
    void testExceptionUrlNotFound() {
        String urlInvalid = "caguei-nas-calças";
        var exception = assertThrows(NotFoundUrl.class, () -> this.urlServices.redirect(urlInvalid));
        assertEquals("Não foi possivel encontrar a URL", exception.getMessage());

    }

    @Test
    void testUrlShortenerExpired() {
        UrlEntity url = new UrlEntity();
        url.setExpiredDate(LocalDateTime.now().minusHours(5));
        when(this.urlEntityRepository.findByUrlShortenerEntity(any())).thenReturn(url);
        var exception = assertThrows(UrlShortenerExpired.class, () -> this.urlServices.redirect(url.getUrlShortener()));
        assertEquals("Url expirado!", exception.getMessage());

    }

}
