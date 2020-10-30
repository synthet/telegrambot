package ru.synthet.telegrambot.action.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.action.ActionContext;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(2)
public class DiceActionHandler extends SendMessageActionHandler {

    private final static String REGEX = "/d(\\d)+";
    private final static Pattern pattern = Pattern.compile(REGEX);

    @Override
    public boolean accept(ActionContext context) {
        Matcher matcher = pattern.matcher(context.getMessage());
        return matcher.matches();
    }

    @Override
    public void process(ActionContext context) {
        sendMessage(context, getMessage(context));
    }

    @Override
    protected String getMessage(ActionContext context) {
        int number = getValue(context);
        return String.valueOf(number);
    }

    private int getValue(ActionContext context) {
        int max = getMax(context.getMessage());
        return max <= 1 ? max : new Random().nextInt(max) + 1;
    }

    private int getMax(String message) {
        return Integer.parseInt(message.replaceAll("[^\\d.]", ""));
    }
}
