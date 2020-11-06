package ru.synthet.telegrambot.component.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.objects.*;
import ru.synthet.telegrambot.component.data.CallbackDataProvider;
import ru.synthet.telegrambot.data.bot.CallbackData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class ActionProcessor {

    private final Logger LOG = LogManager.getLogger(ActionProcessor.class);

    @Autowired
    private List<ActionHandler> handlers;
    @Autowired
    private CallbackDataProvider callbackDataProvider;

    public void processUpdate(Update update) {

        final ActionContext context = getActionContext(update);

        if (valid(context)) {
            handlers.stream()
                    .filter(handler -> handler.accept(context))
                    .findFirst()
                    .ifPresent(handler -> handler.process(context));
        }
    }

    private boolean valid(ActionContext context) {

        if (context.getChatId() == null) {
            return false;
        }

        if (!StringUtils.isEmpty(context.getMessage())) {
            return true;
        }

        if (context.getHasPhoto()) {
            return true;
        }

        return context.getHasCallbackData();
    }

    private ActionContext getActionContext(Update update) {
        ActionContext context = new ActionContext();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Message message = null;
        if (update.getMessage() != null) {
            message = update.getMessage();
        } else if (update.getEditedMessage() != null) {
            message = update.getEditedMessage();
        } else if (callbackQuery != null && callbackQuery.getMessage() != null) {
            message = callbackQuery.getMessage();
        }
        if (message != null) {
            context.setChatId(getChatId(message));
        }
        if (callbackQuery != null) {
            String data = callbackQuery.getData();
            CallbackData callbackData = callbackDataProvider.convert(data);
            context.setCallbackData(callbackData);
            if (callbackData != null) {
                context.setHasCallbackData(true);
            }
        } else if (message != null) {
            String command = getCommand(message);
            context.setMessage(command);
            processPhoto(context, message);
        }
        LOG.info(String.format("Command: %s", context.getMessage()));
        return context;
    }

    private void processPhoto(ActionContext context, Message message) {
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
