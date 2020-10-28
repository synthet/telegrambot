package ru.synthet.telegrambot.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.synthet.telegrambot.action.ActionProcessor;

@Component
public class SynthetBot extends TelegramLongPollingBot {

    private final Logger LOG = LoggerFactory.getLogger(BotInitializer.class);

    @Value("${telegram.bot.token}")
    private String token;

    @Autowired
    private ActionProcessor actionProcessor;

    SynthetBot() {
        super();
    }

    public void onUpdateReceived(Update update) {
        actionProcessor.processUpdate(update);
    }

    public String getBotUsername() {
        return "SynthetBot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public synchronized void sendMessage(String chatId, String message) {
        LOG.info(String.format("Send message: %s", message));
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Exception: ", e);
        }
    }
}
