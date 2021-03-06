package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.integration.animal.cats.Cat;
import ru.synthet.telegrambot.integration.animal.data.ImageType;

import java.util.Arrays;
import java.util.Collection;

@Order(2)
@Component
public class CatActionHandler extends AnimalActionHandler<Cat> {

    @Override
    public String getCommand() {
        return "/cat";
    }

    @Override
    public String getDescription() {
        return "Send me a cat";
    }

    @Override
    protected Collection<ImageType> getImageTypes() {
        return Arrays.asList(ImageType.JPG, ImageType.PNG);
    }
}
