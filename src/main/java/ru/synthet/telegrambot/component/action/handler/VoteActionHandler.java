package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;

@Order(7)
@Component
public class VoteActionHandler extends SendMessageActionHandler {

    @Override
    public String getCommand() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Vote";
    }

    @Override
    public boolean accept(ActionContext actionContext) {
        return actionContext.getHasCallbackData() && actionContext.getCallbackData() instanceof VoteCallbackData;
    }

    @Override
    public void process(ActionContext context) {
        sendMessage(context, getMessage(context));
    }

    @Override
    protected String getMessage(ActionContext context) {
        VoteCallbackData callbackData = (VoteCallbackData) context.getCallbackData();
        return callbackData.getValue() ? EmojiConstants.SMILEY_CAT : EmojiConstants.CRYING_CAT;
    }
}
