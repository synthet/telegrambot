package ru.synthet.telegrambot.component.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.component.data.converter.CallbackDataConverter;
import ru.synthet.telegrambot.data.bot.CallbackData;

import java.util.List;
import java.util.Objects;

@Component
public class CallbackDataProvider {

    @Autowired
    private List<CallbackDataConverter> converters;

    public CallbackData convert(final String value) {
        return converters.stream()
                .filter(converter -> converter.accept(value))
                .map(converter -> converter.convert(value))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
