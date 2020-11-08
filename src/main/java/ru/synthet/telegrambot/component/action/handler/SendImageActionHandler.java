package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.synthet.telegrambot.component.SynthetBot;
import ru.synthet.telegrambot.component.action.ActionContext;

public abstract class SendImageActionHandler extends AbstractActionHandler {

    @Autowired
    private SynthetBot synthetBot;

    void sendImage(ActionContext context, String caption, String url, InlineKeyboardMarkup replyMarkup,
                   boolean isAnimation) {
        String chatId = String.valueOf(context.getChatId());
        if (!isAnimation) {
            synthetBot.sendImage(chatId, caption, url, replyMarkup);
        } else {
            synthetBot.sendAnimation(chatId, caption, url, replyMarkup);
        }
    }
}
