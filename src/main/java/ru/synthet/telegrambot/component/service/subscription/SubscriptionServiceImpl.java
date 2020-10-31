package ru.synthet.telegrambot.component.service.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.synthet.telegrambot.component.dao.subscription.SubscriptionRepository;
import ru.synthet.telegrambot.data.jpa.hibernate.Subscription;
import ru.synthet.telegrambot.data.jpa.hibernate.SubscriptionType;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subscription> getSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subscription> getSubscriptions(SubscriptionType type) {
        return subscriptionRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public Subscription getSubscription(Long chatId, SubscriptionType type) {
        try {
            return subscriptionRepository.findByChatIdAndType(chatId, type);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void saveSubscriptions(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public void saveSubscriptions(Long chatId, SubscriptionType type) {
        Subscription subscription = getSubscription(chatId, type);
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setChatId(chatId);
            subscription.setType(type);
            saveSubscriptions(subscription);
        }
    }
}
