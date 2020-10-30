package ru.synthet.telegrambot.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Optional;

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
            String command = getCommand(update);
            LOG.info(String.format("Command: %s", command));
            actionContext.setMessage(command);
            actionContext.setChatId(update.getMessage().getChatId());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return actionContext;
    }

    private String getCommand(Update update) {
        String command = update.getMessage().getText();
        if (!CollectionUtils.isEmpty(update.getMessage().getEntities())) {
            Optional<MessageEntity> botCommandOptional = update.getMessage()
                    .getEntities()
                    .stream()
                    .filter(e -> e.getType().equals("bot_command"))
                    .findFirst();
            if (botCommandOptional.isPresent()) {
                MessageEntity messageEntity = botCommandOptional.get();
                Integer offset = messageEntity.getOffset();
                Integer length = messageEntity.getLength();
                command = command.substring(offset, offset + length);
            }
        }
        return command;
    }

}
