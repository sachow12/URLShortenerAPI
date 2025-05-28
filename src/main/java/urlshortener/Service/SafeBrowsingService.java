package urlshortener.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SafeBrowsingService {

    private static final String API_URL = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=AIzaSyA5dd8Yn4KBRgRIbsDHLRiSFVtZBKptf3Y";

    public boolean isURLSafe(String url) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = Map.of(
                "client", Map.of(
                        "clientId", "your-client-id",
                        "clientVersion", "1.0"
                ),
                "threatInfo", Map.of(
                        "threatTypes", new String[]{"MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE"},
                        "platformTypes", new String[]{"ANY_PLATFORM"},
                        "threatEntryTypes", new String[]{"URL"},
                        "threatEntries", new Object[]{Map.of("url", url)}
                )
        );

        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );


            return response.getBody() != null && !response.getBody().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
