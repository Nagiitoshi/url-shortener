package url.shortener.url_shortener.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import url.shortener.url_shortener.model.Url;
import url.shortener.url_shortener.service.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {

        String originalUrl = request.get("url");
        String shortUrl = urlService.saveUrl(originalUrl);
        Map<String, String> response = new HashMap<String, String>();
        response.put("url", "https://google.com/" + shortUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        Optional<Url> urOptional = urlService.getOriginalUrl(shortUrl);

        if (urOptional.isPresent()) {
            Url url = urOptional.get();
            System.out.println("Redirecting to: " + url.getOriginalUrl());
            response.sendRedirect(url.getOriginalUrl()); // Usa HttpServletResponse
        } else {
            System.out.println("URL not found or expired: " + shortUrl);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "URL not found");
        }
    }
}
