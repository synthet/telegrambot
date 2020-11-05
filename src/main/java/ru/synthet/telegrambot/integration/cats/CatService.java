package ru.synthet.telegrambot.integration.cats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.service.download.DownloadService;
import ru.synthet.telegrambot.component.service.download.MultipartInputStreamFileResource;
import ru.synthet.telegrambot.integration.cats.datamodel.*;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private final Logger LOG = LogManager.getLogger(CatService.class);

    private static final String X_API_KEY = "x-api-key";
    private static final String BASE_URL = "https://api.thecatapi.com/v1";

    @Value("${cats.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private ObjectMapper objectMapper;

    public Optional<Cat> getCat() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(X_API_KEY, apiKey);
        HttpEntity entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<Cat>> response = restTemplate.exchange(BASE_URL + "/images/search", HttpMethod.GET,
                    entity,
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

    public Optional<VoteResponse> createVote(VoteRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(X_API_KEY, apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<VoteResponse> response = restTemplate.exchange(BASE_URL + "/votes", HttpMethod.POST,
                    entity, VoteResponse.class);
            return Optional.ofNullable(response.getBody());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return Optional.empty();
    }

    public String uploadFile(String fileURL) {
        String result = null;
        try (MultipartInputStreamFileResource fileResource = downloadService.downloadFile(fileURL)) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(X_API_KEY, apiKey);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("file", fileResource);
            HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
            UploadResponse response = restTemplate.postForObject(BASE_URL + "/images/upload", entity,
                    UploadResponse.class);
            if (response != null && response.getApproved() == 1) {
                result = EmojiConstants.SMILEY_CAT2;
            }
        } catch (HttpStatusCodeException ex) {
            LOG.error(ex.getMessage());
            try {
                ErrorResponse error = objectMapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class);
                result = String.format("%s %s", error.getMessage(), EmojiConstants.CRYING_CAT);
            } catch (JsonProcessingException e) {
                LOG.error(e.getMessage());
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return result;
    }
}
