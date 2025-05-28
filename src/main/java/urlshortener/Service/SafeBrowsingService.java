package urlshortener.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class SafeBrowsingService {

    private static final Logger logger = LoggerFactory.getLogger(SafeBrowsingService.class);
    private static final String API_URL = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyA5dd8Yn4KBRgRIbsDHLRiSFVtZBKptf3Y";

    public boolean isURLSafe(String url) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = Map.of(
                "client", Map.of(
                        "clientId", "your-client-id",
                        "clientVersion", "1.0"
                ),
                "threatInfo", Map.of(
                        "threatTypes", List.of("MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE"),
                        "platformTypes", List.of("ANY_PLATFORM"),
                        "threatEntryTypes", List.of("URL"),
                        "threatEntries", List.of(Map.of("url", url))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            logger.info("Sending request to Google Safe Browsing API: {}", requestBody);
            ResponseEntity<Map> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            logger.info("Response from Google Safe Browsing API: {}", response.getBody());

            if (isInBlacklist(url)) {
                return false;
            }

            return response.getBody() == null || response.getBody().isEmpty();
        } catch (Exception e) {
            logger.error("Error while calling Google Safe Browsing API", e);
            return false;
        }
    }

    private boolean isInBlacklist(String url) {
        List<String> blacklist = List.of("https://www.xvideos.com/",
                "https://www.pornhub.com/",
                "https://www.redtube.com/",
                "https://www.xnxx.com/",
                "https://www.youjizz.com/",
                "https://www.tube8.com/",
                "https://www.youporn.com/",
                "https://www.spankwire.com/",
                "https://www.hqporner.com/");
        return blacklist.contains(url);
    }
}
