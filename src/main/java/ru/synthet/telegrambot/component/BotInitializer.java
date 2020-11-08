package ru.synthet.telegrambot.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PreDestroy;

@Component
public class BotInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger LOG = LogManager.getLogger(BotInitializer.class);

    private final SynthetBot synthetBot;

    @Autowired
    BotInitializer(SynthetBot synthetBot) {
        this.synthetBot = synthetBot;
    }

    @PreDestroy
    public void destroy() {
        LOG.info("Shutdown");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Register bot");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(synthetBot);
            LOG.info("Bot registered");
        } catch (Exception ex) {
            LOG.error("Error registering bot", ex);
        }
    }
}
