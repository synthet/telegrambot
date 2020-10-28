package ru.synthet.telegrambot.action;

public interface ActionHandler {

    boolean accept(ActionContext actionContext);

    void process(ActionContext actionContext);
}
