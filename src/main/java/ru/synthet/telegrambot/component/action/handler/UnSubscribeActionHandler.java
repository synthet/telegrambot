package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.component.service.subscription.SubscriptionService;
import ru.synthet.telegrambot.data.jpa.hibernate.SubscriptionType;

@Order(5)
@Component
public class UnSubscribeActionHandler extends SendMessageActionHandler {

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public String getCommand() {
        return "/unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Unsubscribe me";
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
        return subscriptionService.deleteSubscriptions(context.getChatId(), SubscriptionType.CAT) ? "Unsubscribed" : "";
    }
}
