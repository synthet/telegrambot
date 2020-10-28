package ru.synthet.telegrambot.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import ru.synthet.telegrambot.action.ActionContext;
import ru.synthet.telegrambot.component.SynthetBot;

public abstract class SendMessageActionHandler extends AbstractActionHandler {

    @Autowired
    private SynthetBot synthetBot;

    protected abstract String getMessage(ActionContext context);

    void sendMessage(ActionContext context, String message) {
        synthetBot.sendMessage(String.valueOf(context.getChatId()), message);
    }
}
