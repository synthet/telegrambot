package ru.synthet.telegrambot.action.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.action.ActionContext;
import ru.synthet.telegrambot.integration.cats.CatService;

@Component
@Order(3)
public class CatActionHandler extends SendPhotoActionHandler {

    private final Logger LOG = LogManager.getLogger(CatActionHandler.class);

    @Autowired
    private CatService catService;

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals("/cat");
    }

    @Override
    public void process(ActionContext context) {
        sendMessage(context, getMessage(context));
    }

    @Override
    protected String getMessage(ActionContext context) {
        return catService.getCat();
    }

}
