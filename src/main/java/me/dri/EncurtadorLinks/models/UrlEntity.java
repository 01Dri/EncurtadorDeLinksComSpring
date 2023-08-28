package me.dri.EncurtadorLinks.models;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "URL_ENTITY")
public class UrlEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "URL_BASE")
    private String urlBase;

    @Column(name = "URL_SHORTENER")
    private String urlShortener;

    @Column(name = "URL_SHORTENER_DATE_CREATED")
    private Date dateCreatedUrlShortener;
    @Column(name = "URL_EXPIREDDATE")
    private Date expiredDate;

    @Column(name = "expired")
    private Boolean expired;

    public UrlEntity() {

    }

    public UrlEntity(Long id, String urlBase, String urlShortener, Date dateCreatedUrlShortener, Date expiredDate, Boolean expired) {
        this.id = id;
        this.urlBase = urlBase;
        this.urlShortener = urlShortener;
        this.dateCreatedUrlShortener = dateCreatedUrlShortener;
        this.expiredDate = expiredDate;
        this.expired = expired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public String getUrlShortener() {
        return urlShortener;
    }

    public void setUrlShortener(String urlShortener) {
        this.urlShortener = urlShortener;
    }

    public Date getDateCreatedUrlShortener() {
        return dateCreatedUrlShortener;
    }

    public void setDateCreatedUrlShortener(Date dateCreatedUrlShortener) {
        this.dateCreatedUrlShortener = dateCreatedUrlShortener;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }
}

