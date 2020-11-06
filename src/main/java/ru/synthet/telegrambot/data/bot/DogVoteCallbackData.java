package ru.synthet.telegrambot.data.bot;

public class DogVoteCallbackData extends VoteCallbackData {
    public static final String TYPE = "vd";
    public static final String ACTION = "/vote_dog";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getAction() {
        return ACTION;
    }
}
