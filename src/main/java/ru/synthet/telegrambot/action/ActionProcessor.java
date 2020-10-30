package ru.synthet.telegrambot.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.api.objects.Message;
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

        final ActionContext context = getActionContext(update);

        if ((context.getMessage() == null) || (context.getChatId() == null)) {
            return;
        }

        handlers.stream()
                .filter(handler -> handler.accept(context))
                .findFirst()
                .ifPresent(handler -> handler.process(context));
    }

    private ActionContext getActionContext(Update update) {
        Message message = (update.getMessage() != null) ? update.getMessage() : update.getEditedMessage();
        ActionContext actionContext = new ActionContext();
        if (message != null) {
            try {
                String command = getCommand(message);
                LOG.info(String.format("Command: %s", command));
                actionContext.setMessage(command);
                actionContext.setChatId(getChatId(message));
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
            }
        }
        return actionContext;
    }

    private Long getChatId(Message message) {
        return message.getChatId();
    }

    private String getCommand(Message message) {
        String command = message.getText();
        if (!CollectionUtils.isEmpty(message.getEntities())) {
            Optional<MessageEntity> botCommandOptional = message
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
