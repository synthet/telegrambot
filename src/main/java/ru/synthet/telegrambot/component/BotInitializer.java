package ru.synthet.telegrambot.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import ru.synthet.telegrambot.SynthetBot;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BotInitializer {

    private final Logger LOG = LogManager.getLogger(BotInitializer.class);

    private final SynthetBot synthetBot;

    @Autowired
    BotInitializer(SynthetBot synthetBot) {
        this.synthetBot = synthetBot;
    }

    @PostConstruct
    public void init() {
        LOG.info("Initialize bot");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(synthetBot);
            LOG.info("Bot registered");
        } catch (Exception ex) {
            LOG.error("Error registering bot", ex);
        }
    }

    @PreDestroy
    public void destroy() {
        LOG.info("Shutdown");
    }
}
