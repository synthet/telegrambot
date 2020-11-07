package ru.synthet.telegrambot.component.data.converter;

import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.animal.cats.Cat;

@Component
public class CatVoteCallbackDataProvider extends VoteCallbackDataProvider<VoteCallbackData<Cat>> {

}
