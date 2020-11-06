package ru.synthet.telegrambot.data.bot;

public class CatVoteCallbackData extends VoteCallbackData {
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
