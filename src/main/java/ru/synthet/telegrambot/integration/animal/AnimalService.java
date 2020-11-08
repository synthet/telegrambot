package ru.synthet.telegrambot.integration.animal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.service.download.DownloadService;
import ru.synthet.telegrambot.component.service.download.MultipartInputStreamFileResource;
import ru.synthet.telegrambot.integration.animal.data.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class AnimalService<T extends Animal> {

    private static final String X_API_KEY = "x-api-key";
    private final Logger LOG = LogManager.getLogger(AnimalService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private ObjectMapper objectMapper;

    protected abstract String getAPIKey();

    protected abstract String getBaseURL();

    protected abstract ParameterizedTypeReference<List<T>> getListTypeReference();

    public Optional<T> getImage(Collection<ImageType> types) {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity entity = new HttpEntity<>(headers);
        try {
            ParameterizedTypeReference<List<T>> responseType = getListTypeReference();
            String url = getBaseURL() + "/images/search";
            UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);
            if (!CollectionUtils.isEmpty(types)) {
                urlBuilder.queryParam("mime_types", getImageTypes(types));
            }
            ResponseEntity<List<T>> response = restTemplate.exchange(urlBuilder.toUriString(),
                    HttpMethod.GET, entity, responseType);
            List<T> images = response.getBody();
            if (!CollectionUtils.isEmpty(images)) {
                return images.stream().findFirst();
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return Optional.empty();
    }

    private String getImageTypes(Collection<ImageType> types) {
        return types.stream()
                .map(ImageType::name)
                .map(String::toLowerCase)
                .collect(Collectors.joining(","));
    }

    public Optional<VoteResponse> createVote(VoteRequest request) {
        HttpHeaders headers = getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<VoteResponse> response = restTemplate.exchange(getBaseURL() + "/votes", HttpMethod.POST,
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
            HttpHeaders headers = getHttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("file", fileResource);
            HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
            UploadResponse response = restTemplate.postForObject(getBaseURL() + "/images/upload", entity,
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

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(X_API_KEY, getAPIKey());
        return headers;
    }
}
