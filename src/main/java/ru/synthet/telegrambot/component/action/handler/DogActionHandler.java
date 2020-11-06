package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.DogVoteCallbackData;
import ru.synthet.telegrambot.integration.animal.dogs.Dog;

@Order(3)
@Component
public class DogActionHandler extends AnimalActionHandler<Dog, DogVoteCallbackData> {

    @Override
    public String getCommand() {
        return "/dog";
    }

    @Override
    public String getDescription() {
        return "Send me a dog";
    }
}
