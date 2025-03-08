package url.shortener.url_shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import url.shortener.url_shortener.model.Url;

public interface UrlRepository  extends JpaRepository<Url, Long> {

    Optional<Url> findByShortUrl(String shortUrl);

    
}
