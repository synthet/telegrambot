package ru.synthet.telegrambot.data.bot;

import ru.synthet.telegrambot.integration.animal.data.Animal;

public abstract class VoteCallbackData<T extends Animal> implements CallbackData {
    private String imageId;
    private Boolean value;

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getImageId() {
        return imageId;
    }

    public Boolean getValue() {
        return value;
    }

    public abstract String getType();

    @Override
    public String toString() {
        int value = getValue() ? 1 : 0;
        return String.format("%s.%s.%d", getType(), imageId, value);
    }
}
