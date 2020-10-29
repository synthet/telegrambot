package ru.synthet.telegrambot.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.action.ActionContext;

import java.util.Random;

@Component
@Order(2)
public class DiceActionHandler extends SendMessageActionHandler {

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals("/d20");
    }

    @Override
    public void process(ActionContext context) {
        sendMessage(context, getMessage(context));
    }

    @Override
    protected String getMessage(ActionContext context) {
        Random r = new Random();
        int number = r.nextInt(20) + 1;
        return String.valueOf(number);
    }
}
