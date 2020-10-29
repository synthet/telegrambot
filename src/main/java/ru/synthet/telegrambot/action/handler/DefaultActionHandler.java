package ru.synthet.telegrambot.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.action.ActionContext;

@Order(4)
@Component
public class DefaultActionHandler extends SendMessageActionHandler {

    @Override
    public boolean accept(ActionContext context) {
        return false;
    }

    @Override
    public void process(ActionContext context) {
        sendMessage(context, getMessage(context));
    }

    @Override
    protected String getMessage(ActionContext context) {
        return context.getMessage();
    }
}
