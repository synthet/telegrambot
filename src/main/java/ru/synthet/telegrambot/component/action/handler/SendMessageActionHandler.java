package ru.synthet.telegrambot.component.action.handler;

import org.springframework.util.StringUtils;
import ru.synthet.telegrambot.component.action.ActionContext;

public abstract class SendMessageActionHandler extends AbstractActionHandler {

    protected abstract String getMessage(ActionContext context);

    void sendMessage(ActionContext context, String message) {
        if (!StringUtils.isEmpty(message)) {
            Long chatId = context.getChatId();
            synthetBot.sendMessage(String.valueOf(chatId), message);
        }
    }
}
