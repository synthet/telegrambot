package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.data.bot.DogVoteCallbackData;
import ru.synthet.telegrambot.integration.animal.dogs.Dog;

@Order(9)
@Component
public class DogVoteActionHandler extends VoteActionHandler<Dog> {

    @Override
    public String getCommand() {
        return DogVoteCallbackData.ACTION;
    }

    @Override
    protected String getMessage(ActionContext context) {
        DogVoteCallbackData callbackData = (DogVoteCallbackData) context.getCallbackData();
        return callbackData.getValue() ? EmojiConstants.SMILEY_CAT : EmojiConstants.CRYING_CAT;
    }
}
