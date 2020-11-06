package ru.synthet.telegrambot.component.data.converter;

import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.DogVoteCallbackData;

@Component
public class DogVoteCallbackFabric extends VoteCallbackDataFabric<DogVoteCallbackData> {

    @Override
    public DogVoteCallbackData newInstance() {
        return new DogVoteCallbackData();
    }

    @Override
    String getType() {
        return DogVoteCallbackData.TYPE;
    }
}
