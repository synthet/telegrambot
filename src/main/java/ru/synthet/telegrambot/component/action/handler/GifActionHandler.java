package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.integration.animal.cats.Cat;
import ru.synthet.telegrambot.integration.animal.data.ImageType;

import java.util.Collection;
import java.util.Collections;

@Order(10)
@Component
public class GifActionHandler extends AnimalActionHandler<Cat> {

    @Override
    public String getCommand() {
        return "/gif";
    }

    @Override
    public String getDescription() {
        return "Send me a gif";
    }

    @Override
    protected Collection<ImageType> getImageTypes() {
        return Collections.singletonList(ImageType.GIF);
    }
}
