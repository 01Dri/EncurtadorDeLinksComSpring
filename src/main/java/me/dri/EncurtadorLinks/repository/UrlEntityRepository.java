package me.dri.EncurtadorLinks.repository;

import me.dri.EncurtadorLinks.models.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UrlEntityRepository  extends JpaRepository<UrlEntity, Long> {

    @Query("SELECT u FROM UrlEntity u")
    List<UrlEntity> findAllUrl();

    @Query("SELECT u.urlBase FROM UrlEntity u")
    List<String> findAllUrlBase();

    @Query("SELECT u FROM UrlEntity u WHERE u.urlBase = :url")
    UrlEntity findByUrlBase(@Param("url")String url);

    @Query("SELECT u.urlBase FROM UrlEntity u WHERE u.urlShortener = :urlShortener")
    String findByUrlShortener(@Param("urlShortener") String urlShortener);

    @Query("SELECT u FROM UrlEntity u WHERE u.urlShortener = :urlShortener")
    UrlEntity findByUrlShortenerEntity(@Param("urlShortener") String urlShortener);



}
