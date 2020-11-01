package ru.synthet.telegrambot.component.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.component.action.handler.CatActionHandler;
import ru.synthet.telegrambot.component.service.subscription.SubscriptionService;
import ru.synthet.telegrambot.data.jpa.hibernate.Subscription;
import ru.synthet.telegrambot.data.jpa.hibernate.SubscriptionType;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final Logger LOG = LogManager.getLogger(TaskService.class);

    private final CatActionHandler catActionHandler;
    private final SubscriptionService subscriptionService;

    @Autowired
    public TaskService(CatActionHandler catActionHandler,
                       SubscriptionService subscriptionService) {
        this.catActionHandler = catActionHandler;
        this.subscriptionService = subscriptionService;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void sendCats() {
        getChatIds().forEach(this::sendCat);
    }

    private List<Long> getChatIds() {
        return subscriptionService.getSubscriptions(SubscriptionType.CAT)
                .stream()
                .map(Subscription::getChatId)
                .collect(Collectors.toList());
    }

    private void sendCat(Long chatId) {
        try {
            ActionContext context = new ActionContext();
            context.setChatId(chatId);
            context.setMessage("/cat");
            catActionHandler.process(context);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
