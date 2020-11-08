package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.integration.animal.data.ImageType;
import ru.synthet.telegrambot.integration.animal.dogs.Dog;

import java.util.Arrays;
import java.util.Collection;

@Order(3)
@Component
public class DogActionHandler extends AnimalActionHandler<Dog> {

    @Override
    public String getCommand() {
        return "/dog";
    }

    @Override
    public String getDescription() {
        return "Send me a dog";
    }

    @Override
    protected Collection<ImageType> getImageTypes() {
        return Arrays.asList(ImageType.JPG, ImageType.PNG);
    }
}
