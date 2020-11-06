package ru.synthet.telegrambot.integration.animal.cats;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.synthet.telegrambot.integration.animal.AnimalService;

import java.util.List;

@Service
public class CatService extends AnimalService<Cat> {

    private static final String BASE_URL = "https://api.thecatapi.com/v1";

    @Value("${cats.api.key}")
    private String apiKey;

    @Override
    protected String getAPIKey() {
        return apiKey;
    }

    @Override
    protected String getBaseURL() {
        return BASE_URL;
    }

    @Override
    protected ParameterizedTypeReference<List<Cat>> getListTypeReference() {
        return new ParameterizedTypeReference<List<Cat>>() {
        };
    }
}
