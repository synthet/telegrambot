package ru.synthet.telegrambot.integration.cats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.synthet.telegrambot.integration.cats.datamodel.Cat;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private final Logger LOG = LogManager.getLogger(CatService.class);

    private static final String URL = "https://api.thecatapi.com/v1/images/search";

    @Value("${cats.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<Cat> getCat() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<Cat>> response = restTemplate.exchange(URL, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<Cat>>() {
                    });
            List<Cat> cats = response.getBody();
            if (!CollectionUtils.isEmpty(cats)) {
                return cats.stream().findFirst();
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return Optional.empty();
    }
}
