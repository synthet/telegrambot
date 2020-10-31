package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import ru.synthet.telegrambot.component.SynthetBot;
import ru.synthet.telegrambot.component.action.ActionContext;

public abstract class SendImageActionHandler extends AbstractActionHandler {

    @Autowired
    private SynthetBot synthetBot;

    void sendImage(ActionContext context, String caption, String url) {
        synthetBot.sendImage(String.valueOf(context.getChatId()), caption, url);
    }
}
