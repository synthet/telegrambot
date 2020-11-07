package ru.synthet.telegrambot.component.data.converter;

import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.CatVoteCallbackData;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.animal.cats.Cat;

@Component
public class CatVoteCallbackFabric extends VoteCallbackDataFabric<VoteCallbackData<Cat>> {

    @Override
    public CatVoteCallbackData newInstance() {
        return new CatVoteCallbackData();
    }

    @Override
    String getType() {
        return CatVoteCallbackData.TYPE;
    }
}
