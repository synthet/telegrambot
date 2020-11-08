package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.component.data.converter.VoteCallbackDataFabric;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.animal.AnimalService;
import ru.synthet.telegrambot.integration.animal.data.Animal;
import ru.synthet.telegrambot.integration.animal.data.ImageType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AnimalActionHandler<T extends Animal> extends SendImageActionHandler {

    @Autowired
    private AnimalService<T> animalService;
    @Autowired
    private VoteCallbackDataFabric<VoteCallbackData<T>> voteCallbackDataFabric;

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals(getCommand());
    }

    @Override
    public void process(ActionContext context) {
        Collection<ImageType> imageTypes = getImageTypes();
        Optional<T> optionalAnimal = animalService.getImage(imageTypes);
        if (optionalAnimal.isPresent()) {
            Animal animal = optionalAnimal.get();
            boolean isAnimation = imageTypes.contains(ImageType.GIF);
            sendImage(context, getCaption(animal), getUrl(animal), getReplyMarkup(animal), isAnimation);
        }
    }

    protected abstract Collection<ImageType> getImageTypes();

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
        VoteCallbackData<T> voteCallbackDataUp = getCallbackData(imageId, true);
        buttonThumbsUp.setCallbackData(voteCallbackDataUp.toString());
        InlineKeyboardButton buttonThumbsDown = new InlineKeyboardButton();
        buttonThumbsDown.setText(EmojiConstants.THUMBS_DOWN);
        VoteCallbackData<T> voteCallbackDataDown = getCallbackData(imageId, false);
        buttonThumbsDown.setCallbackData(voteCallbackDataDown.toString());
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(buttonThumbsUp);
        keyboardButtonsRow.add(buttonThumbsDown);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private VoteCallbackData<T> getCallbackData(String imageId, boolean value) {
        VoteCallbackData<T> voteCallbackDataUp = voteCallbackDataFabric.newInstance();
        voteCallbackDataUp.setImageId(imageId);
        voteCallbackDataUp.setValue(value);
        return voteCallbackDataUp;
    }
}
