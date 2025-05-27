package urlshortener.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.DTO.URLDTO;
import urlshortener.Service.URLService;

import java.net.URI;

@RestController
@RequestMapping("/")
public class URLController {

    @Autowired
    URLService urlService;

    @PostMapping("api/v1/shorten")
    public ResponseEntity<URLDTO> shortenURL(@RequestBody URLDTO urlDTO) {
        URLDTO shortenedURL = urlService.shortenURL(urlDTO);
        return ResponseEntity.ok(shortenedURL);
    }

    @GetMapping("{shortCode}")
    public ResponseEntity<String> getOriginalURL(@PathVariable  String shortCode) {
        String originalURL = urlService.getOriginalURL(shortCode);
        if (originalURL == null) {
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalURL))
                    .build();

    }
}
