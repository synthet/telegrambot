package ru.synthet.telegrambot.component.data.converter;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.synthet.telegrambot.data.bot.CallbackData;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;

import java.util.Arrays;
import java.util.List;

@Component
public class VoteCallbackDataProvider implements CallbackDataConverter {

    @Override
    public boolean accept(String type) {
        return type.startsWith(VoteCallbackData.TYPE);
    }

    @Override
    public CallbackData convert(String value) {
        List<String> parameters = Arrays.asList(value.split("\\."));
        if (!CollectionUtils.isEmpty(parameters)) {
            return new VoteCallbackData(parameters.get(1), Integer.parseInt(parameters.get(2)) == 1);
        }
        return null;
    }
}
