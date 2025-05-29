package urlshortener.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlshortener.DTO.URLDTO;
import urlshortener.Model.URL;
import urlshortener.Repository.URLRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import static urlshortener.Mapper.ObjectMapper.parseObject;

@Service
public class URLService {


    @Autowired
    URLRepository urlRepository;

    @Autowired
    SafeBrowsingService safeBrowsingService;

    private static final String CHARACTER_LIST = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom secureRandom = new SecureRandom();



    public static String generateNewURL() {
        StringBuilder shortenedURL = new StringBuilder(8);
        for(int i = 0; i < 8; i++) {
            shortenedURL.append(CHARACTER_LIST.charAt(secureRandom.nextInt(CHARACTER_LIST.length())));
        }

        return shortenedURL.toString();
    }

    public URLDTO shortenURL(URLDTO urlDTO){
        if(urlDTO == null || urlDTO.getOriginalUrl() == null || urlDTO.getOriginalUrl().isEmpty()) {
            throw new IllegalArgumentException("No URL provided to shorten.");
        }

        if(safeBrowsingService.isInBlacklist(urlDTO.getOriginalUrl())) {
            throw new IllegalArgumentException("You should shame yourself.");
        }

        String newURL;
        do {
            newURL = generateNewURL();
        } while (urlRepository.findByNewUrl(newURL) != null);

        URL url = new URL();
        url.setOriginalUrl(urlDTO.getOriginalUrl());
        url.setNewUrl(newURL);
        url.setCreationDate(LocalDateTime.now());
        urlRepository.save(url);

        return parseObject(url, URLDTO.class);
    }

    public String getOriginalURL(String shortCode) {
        URL url = urlRepository.findByNewUrl(shortCode);

        if(url != null){
            Long currentCount = url.getAccessCounter();
            if(currentCount == null) {
                url.setAccessCounter(1L);
            } else {
                url.setAccessCounter(currentCount + 1);
            }
            url.setLastAccessDate(LocalDateTime.now());
            urlRepository.save(url);
            return url.getOriginalUrl();
        }

        return null;
    }

}
