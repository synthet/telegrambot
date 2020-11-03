package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import ru.synthet.telegrambot.component.SynthetBot;
import ru.synthet.telegrambot.component.action.ActionHandler;

public abstract class AbstractActionHandler implements ActionHandler {

    @Autowired
    protected SynthetBot synthetBot;

}
