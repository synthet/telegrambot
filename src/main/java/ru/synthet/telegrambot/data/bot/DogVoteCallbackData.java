package ru.synthet.telegrambot.data.bot;

import ru.synthet.telegrambot.integration.animal.dogs.Dog;

public class DogVoteCallbackData extends VoteCallbackData<Dog> {
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
