package ru.synthet.telegrambot.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import ru.synthet.telegrambot.action.ActionContext;
import ru.synthet.telegrambot.component.SynthetBot;

public abstract class SendPhotoActionHandler extends SendMessageActionHandler {

    @Autowired
    private SynthetBot synthetBot;

    @Override
    void sendMessage(ActionContext context, String message) {
        synthetBot.sendImage(String.valueOf(context.getChatId()), message);
    }
}
