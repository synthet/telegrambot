package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.component.service.subscription.SubscriptionService;
import ru.synthet.telegrambot.data.jpa.hibernate.SubscriptionType;

@Order(4)
@Component
public class SubscribeActionHandler extends SendMessageActionHandler {

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public String getCommand() {
        return "/subscribe";
    }

    @Override
    public String getDescription() {
        return "Subscribe me";
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
        return subscriptionService.saveSubscriptions(context.getChatId(), SubscriptionType.CAT) ? "Subscribed" : "";
    }
}
