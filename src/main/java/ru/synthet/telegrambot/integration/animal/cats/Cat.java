package ru.synthet.telegrambot.integration.animal.cats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ru.synthet.telegrambot.integration.animal.AnimalType;
import ru.synthet.telegrambot.integration.animal.data.Animal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cat extends Animal {

    @Override
    public AnimalType getAnimalType() {
        return AnimalType.CAT;
    }
}
