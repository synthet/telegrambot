package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.synthet.telegrambot.component.SynthetBot;
import ru.synthet.telegrambot.component.action.ActionContext;

public abstract class SendMessageActionHandler extends AbstractActionHandler {

    @Autowired
    private SynthetBot synthetBot;

    protected abstract String getMessage(ActionContext context);

    void sendMessage(ActionContext context, String message) {
        if (!StringUtils.isEmpty(message)) {
            Long chatId = context.getChatId();
            synthetBot.sendMessage(String.valueOf(chatId), message);
        }
    }
}
