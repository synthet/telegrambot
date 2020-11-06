package ru.synthet.telegrambot.component.data.converter;

import ru.synthet.telegrambot.data.bot.CallbackData;

public interface CallbackDataConverter {

    boolean accept(String type);

    CallbackData convert(String convert);
}
