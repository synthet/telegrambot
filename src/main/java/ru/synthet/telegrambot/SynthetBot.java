package ru.synthet.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.synthet.telegrambot.component.BotInitializer;

import java.util.Random;

@Component
public class SynthetBot extends TelegramLongPollingBot {

    private final Logger LOG = LoggerFactory.getLogger(BotInitializer.class);

    @Value("${telegram.bot.token}")
    private String token;

    SynthetBot() {
        super();
    }

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        LOG.info(String.format("Received message: %s", message));
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    private synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        if (s.equals("/ping")) {
            s = "pong";
        }
        if (s.equals("/d20")) {
            Random r = new Random();
            int number = r.nextInt(20) + 1;
            s = String.valueOf(number);
        }
        LOG.info(String.format("Send message: %s", s));
        sendMessage.setText(s);
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Exception: ", e);
        }
    }

    public String getBotUsername() {
        return "SynthetBot";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
