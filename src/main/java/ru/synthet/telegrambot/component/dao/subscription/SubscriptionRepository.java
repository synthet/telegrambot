package ru.synthet.telegrambot.component.dao.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.synthet.telegrambot.data.jpa.hibernate.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, SubscriptionCustomRepository {

}
