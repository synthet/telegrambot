package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.animal.AnimalService;
import ru.synthet.telegrambot.integration.animal.data.Animal;
import ru.synthet.telegrambot.integration.animal.data.VoteRequest;
import ru.synthet.telegrambot.integration.animal.data.VoteResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class VoteActionHandler<T extends Animal> extends AbstractActionHandler {

    @Autowired
    private AnimalService<T> animalService;

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public boolean accept(ActionContext actionContext) {
        return actionContext.getHasCallbackData() && actionContext.getCallbackData().getAction().equals(getCommand());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void process(final ActionContext context) {
        VoteCallbackData<T> callbackData = (VoteCallbackData<T>) context.getCallbackData();
        String subId = String.valueOf(context.getChatId());
        String imageId = callbackData.getImageId();
        Boolean value = callbackData.getValue();
        VoteRequest request = new VoteRequest(imageId, subId, value);
        Optional<VoteResponse> vote = animalService.createVote(request);
        vote.ifPresent(voteResponse -> {
            boolean isSuccess = voteResponse.getMessage().equals("SUCCESS");
            if (isSuccess) {
                updateKeyboard(context, value);
            }
        });
    }

    private void updateKeyboard(final ActionContext context, Boolean value) {
        Optional<InlineKeyboardMarkup> replyMarkup = getReplyMarkup(context.getReplyMarkup(), value);
        replyMarkup.ifPresent(markup -> {
            String chatId = String.valueOf(context.getChatId());
            Integer messageId = context.getMessageId();
            synthetBot.updateKeyboard(chatId, messageId, null, markup);
        });
    }

    private Optional<InlineKeyboardMarkup> getReplyMarkup(InlineKeyboardMarkup replyMarkup, Boolean value) {
        List<List<InlineKeyboardButton>> keyboard = replyMarkup.getKeyboard();
        List<InlineKeyboardButton> buttons = keyboard.get(0);
        InlineKeyboardButton buttonUp = buttons.get(0);
        InlineKeyboardButton buttonDown = buttons.get(1);
        String textUp = buttonUp.getText();
        String textDown = buttonDown.getText();
        updateButton(buttonUp, value, true, EmojiConstants.THUMBS_UP);
        updateButton(buttonDown, value, false, EmojiConstants.THUMBS_DOWN);
        if (!textUp.equals(buttonUp.getText()) || !textDown.equals(buttonDown.getText())) {
            return Optional.of(VoteButtonsUtil.getInlineKeyboardMarkup(buttonUp, buttonDown));
        } else {
            return Optional.empty();
        }
    }

    private void updateButton(InlineKeyboardButton button, Boolean value, Boolean choice, String text) {
        String newText = Objects.equals(value, choice) ? getMessage(choice) : text;
        button.setText(newText);
    }

    protected String getMessage(Boolean value) {
        return value ? EmojiConstants.SMILEY_CAT : EmojiConstants.CRYING_CAT;
    }
}
