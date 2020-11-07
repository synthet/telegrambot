package ru.synthet.telegrambot.component.data.converter;

import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.DogVoteCallbackData;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.animal.dogs.Dog;

@Component
public class DogVoteCallbackFabric extends VoteCallbackDataFabric<VoteCallbackData<Dog>> {

    @Override
    public DogVoteCallbackData newInstance() {
        return new DogVoteCallbackData();
    }

    @Override
    String getType() {
        return DogVoteCallbackData.TYPE;
    }
}
