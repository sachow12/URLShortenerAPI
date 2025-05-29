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

    public boolean isInBlacklist(String url) {
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
