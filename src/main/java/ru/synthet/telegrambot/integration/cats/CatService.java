package ru.synthet.telegrambot.integration.cats;

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
import ru.synthet.telegrambot.component.service.download.DownloadService;
import ru.synthet.telegrambot.component.service.download.MultipartInputStreamFileResource;
import ru.synthet.telegrambot.integration.cats.datamodel.Cat;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private final Logger LOG = LogManager.getLogger(CatService.class);

    private static final String URL_SEARCH = "https://api.thecatapi.com/v1/images/search";
    private static final String URL_UPLOAD = "https://api.thecatapi.com/v1/images/upload";

    @Value("${cats.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DownloadService downloadService;

    public Optional<Cat> getCat() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<Cat>> response = restTemplate.exchange(URL_SEARCH, HttpMethod.GET, entity,
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

    public String uploadFile(String fileURL) {
        String response;
        try (MultipartInputStreamFileResource fileResource = downloadService.downloadFile(fileURL)) {
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("file", fileResource);
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
            response = restTemplate.postForObject(URL_UPLOAD, requestEntity, String.class);
            LOG.warn(response);
        } catch (HttpStatusCodeException ex) {
            LOG.error(ex.getMessage());
            response = ex.getResponseBodyAsString();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            response = e.getMessage();
        }
        return response;
    }
}
