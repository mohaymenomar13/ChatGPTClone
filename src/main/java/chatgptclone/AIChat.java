package chatgptclone;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class AIChat {
    static ChatLanguageModel model = OpenAiChatModel.withApiKey("demo");
    public static String chat(String userText) {
        try {
            String response = model.generate(userText);
            return response;
        } catch (Exception e) {
        }
        return "NO INTERNET CONNECTION DETECTED.";
    }

    public static String makeTitle(String userText) {
        try {
            String theTitle = model.generate("Make a very short Title base on this user prompt within only no more than 15 characters: "+userText);
            return theTitle;
        } catch (Exception e) {
        }
        return "NO INTERNET CONNECTION DETECTED.";
    }

    public static String suggestPrompt(String type) {
        String response = model.generate("Make a very short example prompt about "+type+" within only no more than 40 characters:");
        return response;
    }
}
