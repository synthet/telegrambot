package ru.synthet.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class SynthBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(SynthBotApplication.class, args);
    }
}
