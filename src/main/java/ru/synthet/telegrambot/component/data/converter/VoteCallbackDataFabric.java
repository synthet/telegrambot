package ru.synthet.telegrambot.component.data.converter;

import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;

@Component
public abstract class VoteCallbackDataFabric<T extends VoteCallbackData> {

    public abstract T newInstance();

    abstract String getType();
}
