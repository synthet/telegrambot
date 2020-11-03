package ru.synthet.telegrambot.component.action;

public class ActionContext {

    private String message;
    private Long chatId;
    private Boolean hasPhoto;
    private String fileId;

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
