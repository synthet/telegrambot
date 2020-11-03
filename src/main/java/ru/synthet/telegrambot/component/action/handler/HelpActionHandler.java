package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.component.action.ActionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Order(1)
@Component
public class HelpActionHandler extends SendMessageActionHandler {

    @Autowired
    private List<ActionHandler> handlers;

    @Override
    public String getCommand() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "List commands";
    }

    @Override
    public boolean accept(ActionContext context) {
        return context.getMessage().equals(getCommand());
    }

    @Override
    public void process(ActionContext context) {
        sendMessage(context, getMessage(context));
    }

    @Override
    protected String getMessage(ActionContext context) {
        return handlers.stream()
                .filter(h -> !StringUtils.isEmpty(h.getCommand()))
                .map(h -> String.format("%s - %s", h.getCommand(), h.getDescription()))
                .collect(Collectors.joining("\n"));
    }
}
