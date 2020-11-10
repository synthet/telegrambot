package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.CatVoteCallbackData;
import ru.synthet.telegrambot.integration.animal.cats.Cat;

@Order(8)
@Component
public class CatVoteActionHandler extends VoteActionHandler<Cat> {

    @Override
    public String getCommand() {
        return CatVoteCallbackData.ACTION;
    }

}
