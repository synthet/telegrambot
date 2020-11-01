package ru.synthet.telegrambot.component.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.component.action.ActionContext;

@Order(1)
@Component
public class HelpActionHandler extends SendMessageActionHandler {

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals("/help");
    }

    @Override
    public void process(ActionContext context) {
        sendMessage(context, getMessage(context));
    }

    @Override
    protected String getMessage(ActionContext context) {
        return "";
    }
}
