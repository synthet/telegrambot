package ru.synthet.telegrambot.component.action.handler;

import org.springframework.beans.factory.annotation.Autowired;
import ru.synthet.telegrambot.component.EmojiConstants;
import ru.synthet.telegrambot.component.action.ActionContext;
import ru.synthet.telegrambot.data.bot.CatVoteCallbackData;
import ru.synthet.telegrambot.data.bot.VoteCallbackData;
import ru.synthet.telegrambot.integration.animal.AnimalService;
import ru.synthet.telegrambot.integration.animal.data.Animal;
import ru.synthet.telegrambot.integration.animal.data.VoteRequest;
import ru.synthet.telegrambot.integration.animal.data.VoteResponse;

import java.util.Optional;

public abstract class VoteActionHandler<T extends Animal, E extends VoteCallbackData> extends SendMessageActionHandler {

    @Autowired
    private AnimalService<T> animalService;

    @Override
    public String getDescription() {
        return "Vote";
    }

    @Override
    public boolean accept(ActionContext actionContext) {
        return actionContext.getHasCallbackData() && actionContext.getCallbackData().getAction().equals(getCommand());
    }

    @Override
    public void process(final ActionContext context) {
        VoteCallbackData callbackData = (VoteCallbackData) context.getCallbackData();
        String subId = String.valueOf(context.getChatId());
        String imageId = callbackData.getImageId();
        Boolean value = callbackData.getValue();
        VoteRequest request = new VoteRequest(imageId, subId, value);
        Optional<VoteResponse> vote = animalService.createVote(request);
        vote.ifPresent(voteResponse -> {
            boolean isSuccess = voteResponse.getMessage().equals("SUCCESS");
            if (isSuccess) {
                sendMessage(context, getMessage(context));
            }
        });
    }

    @Override
    protected String getMessage(ActionContext context) {
        CatVoteCallbackData callbackData = (CatVoteCallbackData) context.getCallbackData();
        return callbackData.getValue() ? EmojiConstants.SMILEY_CAT : EmojiConstants.CRYING_CAT;
    }
}
