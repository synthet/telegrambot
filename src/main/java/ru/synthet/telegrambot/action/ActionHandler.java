package ru.synthet.telegrambot.action;

import org.telegram.telegrambots.api.objects.Update;

public interface ActionHandler {

    void setNext(ActionHandler actionHandler);

    boolean process(Update update);
}
