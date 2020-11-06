package ru.synthet.telegrambot.component.action.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.component.data.converter.VoteCallbackDataFabric;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.animal.AnimalService;
import ru.synthet.telegrambot.integration.animal.data.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AnimalActionHandler<T extends Animal, E extends VoteCallbackData> extends SendImageActionHandler {

    private final Logger LOG = LogManager.getLogger(AnimalActionHandler.class);

    @Autowired
    private AnimalService<T> animalService;
    @Autowired
    private VoteCallbackDataFabric<E> voteCallbackDataFabric;

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals(getCommand());
    }

    @Override
    public void process(ActionContext context) {
        Optional<T> optionalAnimal = animalService.getImage();
        if (optionalAnimal.isPresent()) {
            Animal animal = optionalAnimal.get();
            sendImage(context, getCaption(animal), getUrl(animal), getReplyMarkup(animal));
        }
    }

    private String getUrl(Animal animal) {
        return animal.getUrl();
    }

    private String getCaption(Animal animal) {
        if (!CollectionUtils.isEmpty(animal.getBreeds())) {
            return animal.getBreeds().get(0).getName();
        }
        if (!CollectionUtils.isEmpty(animal.getCategories())) {
            return "#" + animal.getCategories().get(0).getName();
        }
        return null;
    }

    private InlineKeyboardMarkup getReplyMarkup(Animal animal) {
        String imageId = animal.getId();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonThumbsUp = new InlineKeyboardButton();
        buttonThumbsUp.setText(EmojiConstants.THUMBS_UP);
        E voteCallbackDataUp = getCallbackData(imageId, true);
        buttonThumbsUp.setCallbackData(voteCallbackDataUp.toString());
        InlineKeyboardButton buttonThumbsDown = new InlineKeyboardButton();
        buttonThumbsDown.setText(EmojiConstants.THUMBS_DOWN);
        E voteCallbackDataDown = getCallbackData(imageId, false);
        buttonThumbsDown.setCallbackData(voteCallbackDataDown.toString());
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(buttonThumbsUp);
        keyboardButtonsRow.add(buttonThumbsDown);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private E getCallbackData(String imageId, boolean value) {
        E voteCallbackDataUp = voteCallbackDataFabric.newInstance();
        voteCallbackDataUp.setImageId(imageId);
        voteCallbackDataUp.setValue(value);
        return voteCallbackDataUp;
    }
}
