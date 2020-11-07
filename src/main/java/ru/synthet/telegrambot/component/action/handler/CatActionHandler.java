package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.integration.animal.cats.Cat;

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

}
