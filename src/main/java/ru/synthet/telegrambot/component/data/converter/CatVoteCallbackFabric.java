package ru.synthet.telegrambot.component.data.converter;

import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.CatVoteCallbackData;

@Component
public class CatVoteCallbackFabric extends VoteCallbackDataFabric<CatVoteCallbackData> {

    @Override
    public CatVoteCallbackData newInstance() {
        return new CatVoteCallbackData();
    }

    @Override
    String getType() {
        return CatVoteCallbackData.TYPE;
    }
}
