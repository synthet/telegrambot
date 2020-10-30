package ru.synthet.telegrambot.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.synthet.telegrambot.action.ActionContext;
import ru.synthet.telegrambot.action.handler.CatActionHandler;

import java.util.List;

@EnableScheduling
@Service
public class TaskService {

    private final Logger LOG = LogManager.getLogger(TaskService.class);

    @Autowired
    private CatActionHandler catActionHandler;

    @Value("#{'${telegram.chat.ids}'.split(',')}")
    private List<String> chatIds;

    @Scheduled(fixedRate = 60 * 1000)
    public void sendCats() {
        chatIds.forEach(this::sendCat);
    }

    private void sendCat(String chatId) {
        try {
            ActionContext context = new ActionContext();
            context.setChatId(Long.valueOf(chatId));
            context.setMessage("/cat");
            catActionHandler.process(context);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
