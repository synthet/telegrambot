package ru.synthet.telegrambot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import org.apache.log4j.Logger;

import java.util.Properties;


public class SynthetBot extends TelegramLongPollingBot {

    private final static Logger logger = Logger.getLogger(SynthetBot.class);
    private final String token;

    SynthetBot(Properties prop) {
        super();
        token = prop.getProperty("telegram.bot.token");
    }

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }


    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения.
     */

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Exception: ", e);
        }
    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    public String getBotUsername() {
        return "SynthetBot";
    }


    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */

    @Override
    public String getBotToken() {
        return token;
    }
}
