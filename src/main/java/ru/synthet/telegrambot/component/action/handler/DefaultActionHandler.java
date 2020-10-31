package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.component.action.ActionContext;

@Order(0)
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
