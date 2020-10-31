package ru.synthet.telegrambot.component.dao.subscription;

import org.springframework.stereotype.Repository;
import ru.synthet.telegrambot.data.jpa.hibernate.Subscription;
import ru.synthet.telegrambot.data.jpa.hibernate.SubscriptionType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SubscriptionCustomRepositoryImpl implements SubscriptionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Subscription findByChatIdAndType(Long chatId, SubscriptionType type) {

        String sql = "SELECT s FROM Subscription s WHERE s.chatId = :chatId AND s.type = :type";

        return entityManager.createQuery(sql, Subscription.class)
                .setParameter("chatId", chatId)
                .setParameter("type", type)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Subscription> findByType(SubscriptionType type) {

        String sql = "SELECT s FROM Subscription s WHERE s.type = :type";

        return entityManager.createQuery(sql, Subscription.class)
                .setParameter("type", type)
                .getResultList();
    }
}
