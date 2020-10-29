package ru.synthet.telegrambot.integration.reddit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.synthet.telegrambot.integration.reddit.datamodel.Token;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.UUID;

@Service
public class RedditService {

    private final Logger LOG = LogManager.getLogger(RedditService.class);

    private static final String USER_AGENT = "java:ru.synteht.telegrambot:v0.0.1 (by /u/dmn-synthet)";
    private static final String DEVICE_ID = UUID.randomUUID().toString();
    private static final String OAUTH_TOKEN_URL = "https://ssl.reddit.com/api/v1/access_token";

    @Value("${reddit.app.id}")
    private String appId;
    @Value("${reddit.app.secret}")
    private String appSecret;

    @Autowired
    private RestTemplate restTemplate;

    public String getToken() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "https://oauth.reddit.com/grants/installed_client");
        params.add("device_id", DEVICE_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(appId, appSecret, Charset.defaultCharset());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<Token> response = restTemplate.exchange(OAUTH_TOKEN_URL, HttpMethod.POST, entity, Token.class);
            Token token = response.getBody();
            if (Optional.ofNullable(token).isPresent()) {
                return token.getAccessToken();
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return ex.getMessage();
        }
        return "";
    }
}
