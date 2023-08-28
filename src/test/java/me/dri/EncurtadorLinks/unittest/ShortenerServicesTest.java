package me.dri.EncurtadorLinks.unittest;


import me.dri.EncurtadorLinks.exceptions.NotFoundUrl;
import me.dri.EncurtadorLinks.exceptions.UrlFormatInvalid;
import me.dri.EncurtadorLinks.models.UrlEntity;
import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import me.dri.EncurtadorLinks.services.UrlServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShortenerServicesTest {

    @Mock
    UrlEntityRepository urlEntityRepository;

    @InjectMocks
    UrlServices urlServices;




    @Test
    void testGenerateShortenerUrl()  {
        String urlMock = "https://twitter.com/home";
        UrlEntity urlEntity = new UrlEntity(1L, urlMock, "urlEncurtada", new Date(), this.urlServices.generateExpirationDate(), false);
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
        UrlEntity urlEntity = new UrlEntity(1L, "urlBase", "urlEncurtada", new Date(), this.urlServices.generateExpirationDate(), false);
        when(this.urlEntityRepository.findByUrlShortener(any())).thenReturn(urlEntity);
        var result = this.urlServices.redirect(urlEntity.getUrlShortener());
        assertNotNull(result);
        assertEquals(urlEntity.getUrlBase(), result.getUrlBase());

    }

    @Test
    void testExceptionUrlNotFound() {
        String urlInvalid = "caguei-nas-calças";
        assertThrows(NotFoundUrl.class, () -> this.urlServices.redirect(urlInvalid));

    }

}
