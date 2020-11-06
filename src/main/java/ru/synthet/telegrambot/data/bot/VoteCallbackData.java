package ru.synthet.telegrambot.data.bot;

public class VoteCallbackData implements CallbackData {
    public static final String TYPE = "v";
    private static final String ACTION = "/vote";
    private String imageId;
    private Boolean value;

    public VoteCallbackData(String imageId, Boolean value) {
        this.imageId = imageId;
        this.value = value;
    }

    @Override
    public String getAction() {
        return ACTION;
    }

    public String getImageId() {
        return imageId;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        int value = getValue() ? 1 : 0;
        return String.format("%s.%s.%d", TYPE, imageId, value);
    }
}
