package me.dri.EncurtadorLinks.repository;

import me.dri.EncurtadorLinks.models.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UrlEntityRepository  extends JpaRepository<Long, UrlEntity> {
}
