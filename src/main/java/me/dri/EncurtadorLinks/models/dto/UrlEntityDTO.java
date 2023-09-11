package me.dri.EncurtadorLinks.models.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public record UrlEntityDTO(Long id, String urlBase, String urlShortener, LocalDateTime dateCreatedUrlShortener, LocalDateTime expiredDate, Boolean expired) {


}
