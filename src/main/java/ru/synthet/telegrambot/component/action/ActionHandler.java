package ru.synthet.telegrambot.component.action;

public interface ActionHandler {

    boolean accept(ActionContext actionContext);

    void process(ActionContext actionContext);
}
