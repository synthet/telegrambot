package ru.synthet.telegrambot.data.bot;

import java.io.Serializable;

public interface CallbackData extends Serializable {

    String getAction();
}
