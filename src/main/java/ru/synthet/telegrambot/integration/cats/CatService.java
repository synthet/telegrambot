package ru.synthet.telegrambot.integration.cats;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CatService {

    private final Logger LOG = LogManager.getLogger(CatService.class);

    private static final String URL = "https://api.thecatapi.com/v1/images/search";

    @Value("${cats.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public String getCat() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<ArrayNode> response = restTemplate.exchange(URL, HttpMethod.GET, entity, ArrayNode.class);
            ArrayNode arrayNode = response.getBody();
            if (Optional.ofNullable(arrayNode).isPresent()) {
                return arrayNode.get(0).get("url").textValue();
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return ex.getMessage();
        }
        return "";
    }
}
