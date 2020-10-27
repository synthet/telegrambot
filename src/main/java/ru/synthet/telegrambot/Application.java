package ru.synthet.telegrambot;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

import java.io.InputStream;
import java.util.Properties;

public class Application {

    private final static Logger logger = Logger.getLogger(SynthetBot.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try (InputStream input = Application.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            telegramBotsApi.registerBot(new SynthetBot(prop));
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
    }
}
