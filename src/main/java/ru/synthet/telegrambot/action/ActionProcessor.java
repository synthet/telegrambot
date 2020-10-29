package ru.synthet.telegrambot.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

@Component
public class ActionProcessor {

    private final Logger LOG = LogManager.getLogger(ActionProcessor.class);

    @Autowired
    private List<ActionHandler> handlers;

    public void processUpdate(Update update) {

        final ActionContext actionContext = getActionContext(update);

        handlers.stream()
                .filter(handler -> handler.accept(actionContext))
                .findFirst()
                .ifPresent(handler -> handler.process(actionContext));
    }

    private ActionContext getActionContext(Update update) {
        ActionContext actionContext = new ActionContext();
        try {
            String message = update.getMessage().getText();
            LOG.info(String.format("Message: %s", message));
            actionContext.setMessage(message);
            actionContext.setChatId(update.getMessage().getChatId());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return actionContext;
    }

}
