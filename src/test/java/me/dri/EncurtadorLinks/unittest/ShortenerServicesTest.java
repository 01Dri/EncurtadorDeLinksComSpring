package me.dri.EncurtadorLinks.unittest;


import me.dri.EncurtadorLinks.repository.UrlEntityRepository;
import me.dri.EncurtadorLinks.services.UrlServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith((MockitoExtension.class))
public class ShortenerServicesTest {

    @Mock
    UrlEntityRepository urlEntityRepository;


    @InjectMocks
    UrlServices urlServices;



    @Test
    void testGenerateShortenerUrl() {
        String urlMock = "https://twitter.com/home";
        String urlGeneratedByUrlServices = this.urlServices.getShortenerUrl(urlMock);
        assertNotNull(urlGeneratedByUrlServices);
        verify(this.urlEntityRepository.save(any()));



    }


}
