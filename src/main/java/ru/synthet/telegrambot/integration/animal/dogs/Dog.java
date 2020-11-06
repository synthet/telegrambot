package ru.synthet.telegrambot.integration.animal.dogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ru.synthet.telegrambot.integration.animal.AnimalType;
import ru.synthet.telegrambot.integration.animal.data.Animal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dog extends Animal {

    @Override
    public AnimalType getAnimalType() {
        return AnimalType.DOG;
    }
}
