package ru.synthet.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableScheduling
@EnableJpaRepositories("ru.synthet.telegrambot.component.dao")
@SpringBootApplication
public class SynthBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(SynthBotApplication.class, args);
    }
}
