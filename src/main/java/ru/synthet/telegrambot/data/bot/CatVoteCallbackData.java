package ru.synthet.telegrambot.data.bot;

import ru.synthet.telegrambot.integration.animal.cats.Cat;

public class CatVoteCallbackData extends VoteCallbackData<Cat> {
    public static final String TYPE = "vc";
    public static final String ACTION = "/vote_cat";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getAction() {
        return ACTION;
    }
}
