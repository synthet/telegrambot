package ru.synthet.telegrambot.component.service.subscription;

import ru.synthet.telegrambot.data.jpa.hibernate.Subscription;
import ru.synthet.telegrambot.data.jpa.hibernate.SubscriptionType;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> getSubscriptions();

    List<Subscription> getSubscriptions(SubscriptionType type);

    Subscription getSubscription(Long chatId, SubscriptionType type);

    void saveSubscriptions(Subscription subscription);

    boolean saveSubscriptions(Long chatId, SubscriptionType type);

    boolean deleteSubscriptions(Long chatId, SubscriptionType type);
}
