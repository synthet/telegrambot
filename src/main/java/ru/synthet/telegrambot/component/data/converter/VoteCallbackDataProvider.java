package ru.synthet.telegrambot.component.data.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import ru.synthet.telegrambot.data.bot.CallbackData;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;

import java.util.Arrays;
import java.util.List;

public abstract class VoteCallbackDataProvider<T extends VoteCallbackData<?>> implements CallbackDataConverter {

    @Autowired
    private VoteCallbackDataFabric<T> voteCallbackDataFabric;

    @Override
    public boolean accept(String type) {
        return type.startsWith(voteCallbackDataFabric.getType());
    }

    @Override
    public CallbackData convert(String value) {
        List<String> parameters = Arrays.asList(value.split("\\."));
        if (!CollectionUtils.isEmpty(parameters)) {
            T instance = voteCallbackDataFabric.newInstance();
            instance.setImageId(parameters.get(1));
            instance.setValue(Integer.parseInt(parameters.get(2)) == 1);
            return instance;
        }
        return null;
    }
}
