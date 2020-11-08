package ru.synthet.telegrambot.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.synthet.telegrambot.component.action.ActionProcessor;
import ru.synthet.telegrambot.component.service.download.DownloadService;
import ru.synthet.telegrambot.component.service.download.MultipartInputStreamFileResource;

import java.io.IOException;

@Component
public class SynthetBot extends TelegramLongPollingBot {

    private static final String BOT_NAME = "SynthetBot";
    private final Logger LOG = LogManager.getLogger(SynthetBot.class);

    @Value("${telegram.bot.token}")
    private String token;

    @Autowired
    private ActionProcessor actionProcessor;
    @Autowired
    private DownloadService downloadService;

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

    public void sendMessage(String chatId, String message) {
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

    public String getFileURL(String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);
        String filePath = execute(getFile).getFilePath();
        return File.getFileUrl(getBotToken(), filePath);
    }

    public void sendImage(String chatId, String caption, String fileURL,
                          InlineKeyboardMarkup replyMarkup) {
        LOG.info(String.format("Send image: %s", fileURL));
        SendPhoto sendPhoto = new SendPhoto();
        try (MultipartInputStreamFileResource fileResource = getFileResource(fileURL)) {
            InputFile inputFile = getInputFile(fileResource);
            sendPhoto.setPhoto(inputFile);
            sendPhoto.setChatId(chatId);
            sendPhoto.setCaption(caption);
            sendPhoto.setReplyMarkup(replyMarkup);
            execute(sendPhoto);
        } catch (TelegramApiException | IOException e) {
            LOG.error("Exception: ", e);
        }
    }

    public void sendAnimation(String chatId, String caption, String fileURL,
                              InlineKeyboardMarkup replyMarkup) {
        LOG.info(String.format("Send animation: %s", fileURL));
        SendAnimation sendAnimation = new SendAnimation();
        try (MultipartInputStreamFileResource fileResource = getFileResource(fileURL)) {
            InputFile inputFile = getInputFile(fileResource);
            sendAnimation.setAnimation(inputFile);
            sendAnimation.setChatId(chatId);
            sendAnimation.setCaption(caption);
            sendAnimation.setReplyMarkup(replyMarkup);
            execute(sendAnimation);
        } catch (TelegramApiException | IOException e) {
            LOG.error("Exception: ", e);
        }
    }

    private InputFile getInputFile(MultipartInputStreamFileResource fileResource) throws IOException {
        return new InputFile(fileResource.getInputStream(), fileResource.getFilename());
    }

    private MultipartInputStreamFileResource getFileResource(String fileURL) {
        return downloadService.downloadFile(fileURL);
    }
}
