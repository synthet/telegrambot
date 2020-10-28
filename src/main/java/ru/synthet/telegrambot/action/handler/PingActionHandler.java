package ru.synthet.telegrambot.action.handler;

import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.action.ActionContext;

@Component
public class PingActionHandler extends SendMessageActionHandler {

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals("/ping");
    }

    @Override
    public void process(ActionContext context) {
        sendMessage(context, getMessage(context));
    }

    @Override
    protected String getMessage(ActionContext context) {
        return "pong";
    }
}
