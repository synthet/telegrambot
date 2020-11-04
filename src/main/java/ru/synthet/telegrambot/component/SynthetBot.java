package ru.synthet.telegrambot.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.synthet.telegrambot.component.action.ActionProcessor;

@Component
public class SynthetBot extends TelegramLongPollingBot {

    private static final String BOT_NAME = "SynthetBot";
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
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public synchronized void sendMessage(String chatId, String message) {
        LOG.info(String.format("Send message: %s", message));
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Exception: ", e);
        }
    }

    public synchronized void sendImage(String chatId, String caption, String url, InlineKeyboardMarkup replyMarkup) {
        LOG.info(String.format("Send image: %s", url));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(url);
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(caption);
        sendPhoto.setReplyMarkup(replyMarkup);
        try {
            sendPhoto(sendPhoto);
        } catch (TelegramApiException e) {
            LOG.error("Exception: ", e);
        }
    }

    public String getFileURL(String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile().setFileId(fileId);
        String filePath = execute(getFile).getFilePath();
        return File.getFileUrl(getBotToken(), filePath);
    }
}
