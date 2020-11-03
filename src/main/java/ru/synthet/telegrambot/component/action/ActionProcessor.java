package ru.synthet.telegrambot.component.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class ActionProcessor {

    private final Logger LOG = LogManager.getLogger(ActionProcessor.class);

    @Autowired
    private List<ActionHandler> handlers;

    public void processUpdate(Update update) {

        final ActionContext context = getActionContext(update);

        if ((StringUtils.isEmpty(context.getMessage()) && !context.getHasPhoto()) || (context.getChatId() == null)) {
            return;
        }

        handlers.stream()
                .filter(handler -> handler.accept(context))
                .findFirst()
                .ifPresent(handler -> handler.process(context));
    }

    private ActionContext getActionContext(Update update) {
        Message message = (update.getMessage() != null) ? update.getMessage() : update.getEditedMessage();
        ActionContext context = new ActionContext();
        if (message != null) {
            try {
                String command = getCommand(message);
                LOG.info(String.format("Command: %s", command));
                context.setMessage(command);
                context.setChatId(getChatId(message));
                processPhoto(message, context);
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
            }
        }
        return context;
    }

    private void processPhoto(Message message, ActionContext context) {
        List<PhotoSize> photoSizes = message.getPhoto();
        if (!CollectionUtils.isEmpty(photoSizes)) {
            Optional<String> optionalPhoto = photoSizes.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getFileId);
            optionalPhoto.ifPresent(context::setFileId);
            context.setHasPhoto(true);
        }
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
