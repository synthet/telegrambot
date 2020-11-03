package ru.synthet.telegrambot.component.service.download;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DownloadService {

    private final Logger LOG = LogManager.getLogger(DownloadService.class);

    @Autowired
    private RestTemplate restTemplate;

    public MultipartInputStreamFileResource downloadFile(String fileURL) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity entity = new HttpEntity<>(headers);
            ResponseEntity<Resource> responseEntity = restTemplate.exchange(fileURL, HttpMethod.GET, entity, Resource.class);
            Resource resource = responseEntity.getBody();
            long contentLength = responseEntity.getHeaders().getContentLength();
            String name = FilenameUtils.getName(fileURL);
            return new MultipartInputStreamFileResource(resource.getInputStream(), name, contentLength);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return null;
        }
    }
}
