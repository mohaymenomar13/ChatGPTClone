package chatgptclone;

public class Account {
    private String emailAddress, password, chatHistory, chatSections;
    private int currentChatSection = -1;

    public Account(String emailAddress, String password, String chatHistory, String chatSections) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.chatHistory = chatHistory;
        this.chatSections = chatSections;
    }

    //Setters
    public void setCurrentChatSection(int index) {
        this.currentChatSection = index;
    }
    public void setChatHistory(String chatHistory) {
        this.chatHistory = chatHistory;
    }
    public void setChatSection(String chatSections) {
        this.chatSections = chatSections;
    }

    //Getters
    public String getEmailAddress() { return emailAddress; }
    public String getPassword() { return password; }
    public String getChatHistory() { return chatHistory; }
    public String getChatSections() { return chatSections; }
    public int getCurrentChatSection() { return currentChatSection; }
}