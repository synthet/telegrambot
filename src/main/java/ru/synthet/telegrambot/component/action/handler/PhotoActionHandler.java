package ru.synthet.telegrambot.component.action.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.integration.animal.cats.CatService;

@Order(6)
@Component
public class PhotoActionHandler extends SendMessageActionHandler {

    private final Logger LOG = LogManager.getLogger(PhotoActionHandler.class);

    @Autowired
    private CatService catService;

    @Override
    public String getCommand() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public boolean accept(ActionContext context) {
        return context.getHasPhoto();
    }

    @Override
    public void process(ActionContext actionContext) {
        try {
            String fileURL = synthetBot.getFileURL(actionContext.getFileId());
            String result = catService.uploadFile(fileURL);
            sendMessage(actionContext, result);
        } catch (TelegramApiException ex) {
            LOG.error(ex.getMessage());
        }
    }

    @Override
    protected String getMessage(ActionContext context) {
        return "";
    }
}
