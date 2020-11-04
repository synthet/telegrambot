package ru.synthet.telegrambot.data.bot;

public interface CallbackData {

    static CallbackData getInstance(String value) {
        return CallbackDataUtils.getCallbackData(value);
    }

    String getAction();
}
