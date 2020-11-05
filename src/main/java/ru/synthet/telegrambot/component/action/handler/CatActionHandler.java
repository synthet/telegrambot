package ru.synthet.telegrambot.component.action.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.cats.CatService;
import ru.synthet.telegrambot.integration.cats.datamodel.Cat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Order(3)
@Component
public class CatActionHandler extends SendImageActionHandler {

    private final Logger LOG = LogManager.getLogger(CatActionHandler.class);

    @Autowired
    private CatService catService;

    @Override
    public String getCommand() {
        return "/cat";
    }

    @Override
    public String getDescription() {
        return "Send me a cat";
    }

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals(getCommand());
    }

    @Override
    public void process(ActionContext context) {
        Optional<Cat> optionalCat = catService.getCat();
        if (optionalCat.isPresent()) {
            Cat cat = optionalCat.get();
            sendImage(context, getCaption(cat), getUrl(cat), getReplyMarkup(cat));
        }
    }

    private String getUrl(Cat cat) {
        return cat.getUrl();
    }

    private String getCaption(Cat cat) {
        if (!CollectionUtils.isEmpty(cat.getBreeds())) {
            return cat.getBreeds().get(0).getName();
        }
        if (!CollectionUtils.isEmpty(cat.getCategories())) {
            return "#" + cat.getCategories().get(0).getName();
        }
        return null;
    }

    private InlineKeyboardMarkup getReplyMarkup(Cat cat) {
        String imageId = cat.getId();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonThumbsUp = new InlineKeyboardButton();
        buttonThumbsUp.setText(EmojiConstants.THUMBS_UP);
        buttonThumbsUp.setCallbackData(new VoteCallbackData(imageId, true).toString());
        InlineKeyboardButton buttonThumbsDown = new InlineKeyboardButton();
        buttonThumbsDown.setText(EmojiConstants.THUMBS_DOWN);
        buttonThumbsDown.setCallbackData(new VoteCallbackData(imageId, false).toString());
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(buttonThumbsUp);
        keyboardButtonsRow.add(buttonThumbsDown);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
