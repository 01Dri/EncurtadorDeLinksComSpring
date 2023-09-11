package me.dri.EncurtadorLinks.models.dto;

import java.time.ZonedDateTime;

public record UrlEntityDTO(Long id, String urlBase, String urlShortener, ZonedDateTime dateCreatedUrlShortener, ZonedDateTime expiredDate, Boolean expired) {


}
