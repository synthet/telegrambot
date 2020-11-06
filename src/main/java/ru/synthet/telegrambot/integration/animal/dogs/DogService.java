package ru.synthet.telegrambot.integration.animal.dogs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.synthet.telegrambot.integration.animal.AnimalService;

import java.util.List;

@Service
public class DogService extends AnimalService<Dog> {

    private static final String BASE_URL = "https://api.thedogapi.com/v1";

    @Value("${dogs.api.key}")
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
    protected ParameterizedTypeReference<List<Dog>> getListTypeReference() {
        return new ParameterizedTypeReference<List<Dog>>() {
        };
    }
}
