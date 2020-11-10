package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.DogVoteCallbackData;
import ru.synthet.telegrambot.integration.animal.dogs.Dog;

@Order(9)
@Component
public class DogVoteActionHandler extends VoteActionHandler<Dog> {

    @Override
    public String getCommand() {
        return DogVoteCallbackData.ACTION;
    }

}
