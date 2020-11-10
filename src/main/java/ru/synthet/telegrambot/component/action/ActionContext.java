package ru.synthet.telegrambot.component.action;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.synthet.telegrambot.data.bot.CallbackData;

public class ActionContext {
    private Integer messageId;
    private String message;
    private Long chatId;
    private Boolean hasPhoto;
    private Boolean hasCallbackData;
    private String fileId;
    private CallbackData callbackData;
    private InlineKeyboardMarkup replyMarkup;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return (message != null) ? message.trim() : "";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Boolean getHasPhoto() {
        return (hasPhoto != null) && (hasPhoto);
    }

    public void setHasPhoto(Boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public Boolean getHasCallbackData() {
        return (hasCallbackData != null) && (hasCallbackData);
    }

    public void setHasCallbackData(Boolean hasCallbackData) {
        this.hasCallbackData = hasCallbackData;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public CallbackData getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(CallbackData callbackData) {
        this.callbackData = callbackData;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public void setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }
}
