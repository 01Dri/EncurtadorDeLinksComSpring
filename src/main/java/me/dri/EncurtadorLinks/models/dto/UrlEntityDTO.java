package me.dri.EncurtadorLinks.models.dto;

import java.time.Instant;

public record UrlEntityDTO(Long id, String urlBase, String urlShortener, Instant dateCreatedUrlShortener, Instant expiredDate, Boolean expired) {


}
