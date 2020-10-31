package ru.synthet.telegrambot.component.dao.subscription;

import ru.synthet.telegrambot.data.jpa.hibernate.Subscription;
import ru.synthet.telegrambot.data.jpa.hibernate.SubscriptionType;

import java.util.List;

public interface SubscriptionCustomRepository {

    Subscription findByChatIdAndType(Long chatId, SubscriptionType type);

    List<Subscription> findByType(SubscriptionType type);
}
