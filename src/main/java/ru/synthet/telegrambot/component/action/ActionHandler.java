package ru.synthet.telegrambot.component.action;

public interface ActionHandler {

    String getCommand();

    String getDescription();

    boolean accept(ActionContext actionContext);

    void process(ActionContext actionContext);
}
