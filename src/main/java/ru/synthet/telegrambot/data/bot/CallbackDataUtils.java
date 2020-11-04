package ru.synthet.telegrambot.data.bot;

class CallbackDataUtils {

    static CallbackData getCallbackData(String value) {
        String[] parts = value.split("_");
        if (parts.length >= 2) {
            String partType = parts[0];
            switch (partType) {
                case "v":
                    return getVoteCallbackData(parts);
            }
        }
        return null;
    }

    private static CallbackData getVoteCallbackData(String[] parts) {
        if (parts.length >= 3) {
            int value = Integer.parseInt(parts[2]);
            return new VoteCallbackData(parts[1], value == 1);
        }
        return null;
    }
}
