package ru.synthet.telegrambot.action;

import org.telegram.telegrambots.api.objects.Update;

public abstract class AbstractActionHandler implements ActionHandler {

    private ActionHandler next;

    public void setNext(ActionHandler next) {
        this.next = next;
    }

    public boolean process(Update update) {
        if (next != null) {
            return next.process(update);
        }
        return false;
    }
}
