package ru.synthet.telegrambot.action.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.synthet.telegrambot.action.ActionContext;
import ru.synthet.telegrambot.integration.cats.CatService;
import ru.synthet.telegrambot.integration.cats.datamodel.Cat;

import java.util.Optional;

@Component
@Order(3)
public class CatActionHandler extends SendImageActionHandler {

    private final Logger LOG = LogManager.getLogger(CatActionHandler.class);

    @Autowired
    private CatService catService;

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals("/cat");
    }

    @Override
    public void process(ActionContext context) {
        Optional<Cat> optionalCat = catService.getCat();
        if (optionalCat.isPresent()) {
            Cat cat = optionalCat.get();
            sendImage(context, getCaption(cat), cat.getUrl());
        }
    }

    private String getCaption(Cat cat) {
        if (!CollectionUtils.isEmpty(cat.getBreeds())) {
            return cat.getBreeds().get(0).getName();
        }
        if (!CollectionUtils.isEmpty(cat.getCategories())) {
            return cat.getCategories().get(0).getName();
        }
        return null;
    }

}
