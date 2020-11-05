package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.cats.CatService;
import ru.synthet.telegrambot.integration.cats.datamodel.VoteRequest;
import ru.synthet.telegrambot.integration.cats.datamodel.VoteResponse;

import java.util.Optional;

@Order(7)
@Component
public class VoteActionHandler extends SendMessageActionHandler {

    @Autowired
    private CatService catService;

    @Override
    public String getCommand() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Vote";
    }

    @Override
    public boolean accept(ActionContext actionContext) {
        return actionContext.getHasCallbackData() && actionContext.getCallbackData() instanceof VoteCallbackData;
    }

    @Override
    public void process(final ActionContext context) {
        VoteCallbackData callbackData = (VoteCallbackData) context.getCallbackData();
        String subId = String.valueOf(context.getChatId());
        String imageId = callbackData.getImageId();
        Boolean value = callbackData.getValue();
        VoteRequest request = new VoteRequest(imageId, subId, value);
        Optional<VoteResponse> vote = catService.createVote(request);
        vote.ifPresent(voteResponse -> {
            boolean isSuccess = voteResponse.getMessage().equals("SUCCESS");
            if (isSuccess) {
                sendMessage(context, getMessage(context));
            }
        });
    }

    @Override
    protected String getMessage(ActionContext context) {
        VoteCallbackData callbackData = (VoteCallbackData) context.getCallbackData();
        return callbackData.getValue() ? EmojiConstants.SMILEY_CAT : EmojiConstants.CRYING_CAT;
    }
}
