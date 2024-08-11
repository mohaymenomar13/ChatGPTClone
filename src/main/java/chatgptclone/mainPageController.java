package chatgptclone;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class mainPageController extends MainSceneController{

    
    public static ArrayList<String> chatSections = new ArrayList<String>();
    public static ArrayList<String> chatHistory = new ArrayList<String>();
    public static ArrayList<String> chats = new ArrayList<String>();
    public static String newTitleSection = "";

    @FXML
    private TextField textField;
    @FXML
    private Text pfp2L;
    @FXML
    private VBox chatBoard;
    @FXML
    private ScrollPane chatBoardScrollPane;
    @FXML
    private Pane newTab;
    @FXML
    private VBox chatSectionPanel;
    @FXML
    private Text suggestPromptIdea;
    @FXML
    private Text suggestPromptKnowledge;
    @FXML
    private Text suggestPromptProgramming;
    @FXML
    private Text suggestPromptShopping;
    
    public void initialize()  {
        System.out.println("Initializing");
        chatSections = new ArrayList<String>();
        chatHistory = new ArrayList<String>();
        chats = new ArrayList<String>();

        pfp2L.setText(accounts.get(indexOfPresentAccount).getEmailAddress().substring(0, 2).toUpperCase());

        System.out.println(1);
        //Parsing and splitting the ChatSections and ChatHistory
        String[] splitString = accounts.get(indexOfPresentAccount).getChatSections().split(",");
        for (String section : splitString) {
            chatSections.add(section);
        }

        System.out.println(2);
        String history = accounts.get(indexOfPresentAccount).getChatHistory();
        String parsedHistory = "";
        System.out.println(chatSections.size());
        for (int i = 0; i < chatSections.size(); i++) {
            if (history.length() > 0) {
                
                if (i == 0 && chatSections.size() == 1) { //First part
                    System.out.println(2.1);
                    parsedHistory = history.substring(chatSections.get(0).length()+4);
                } else if (i == 0 && chatSections.size() > 1) { //First part
                    System.out.println(2.2);
                    parsedHistory = history.substring(chatSections.get(0).length()+4, history.indexOf("[|]"+chatSections.get(i+1)));
                } else if (i  == chatSections.size()-1) { //Last part
                    System.out.println(2.3);
                    parsedHistory = history.substring(history.indexOf("[|]"+chatSections.get(i))+chatSections.get(i).length()+4);
                } else if (i != chatSections.size()) {
                    System.out.println(2.4);
                    parsedHistory = history.substring(history.indexOf("[|]"+chatSections.get(i))+chatSections.get(i).length()+4, history.indexOf("[|]"+chatSections.get(i+1)));
                }
            }
            chatHistory.add(parsedHistory);
        }

        System.out.println(3);
        displayChatSections(chatSections, chatHistory, chats);
        displayChatHistory(chatSections, chatHistory, chats);
        suggestPrompts(suggestPromptIdea, suggestPromptKnowledge, suggestPromptShopping, suggestPromptProgramming);
        saveData();
        System.out.println(4);
    }

    public void displayChatSections(ArrayList<String> chatSections, ArrayList<String> chatHistory, ArrayList<String> chats) {
        //Adding chat sections to the left side panel
        chatSectionPanel.getChildren().clear();
        for (int i = 0; i < chatSections.size(); i++) {
            int indexOfCurrentSection = i;
            
            Label label = new Label();
            label.setId(Integer.toString(i));
            label.setText(chatSections.get(i));
            label.getStyleClass().add("chatSectionPanel");

            Image imagee = new Image(getClass().getResourceAsStream("/chatgptclone/img/trashcan.png"));
            ImageView image = new ImageView(imagee);
            ColorAdjust color = new ColorAdjust();
            color.setBrightness(1.0);
            image.setEffect(color);
            image.setPreserveRatio(true);
            image.setFitHeight(25);
            image.setFitWidth(25);
            image.setX(255);
            image.setY(5);

            if (i == accounts.get(indexOfPresentAccount).getCurrentChatSection()) {
                image.setVisible(true);
            } else {
                image.setVisible(false);
            }

            image.setOnMouseClicked((MouseEvent event) -> {
                int index = Integer.valueOf(label.getId());
                //Deleting the Chat History
                String oldChatHistory = accounts.get(indexOfPresentAccount).getChatHistory();
                String toBeDeleteHistory = "[|]"+chatSections.get(index)+"|"+chatHistory.get(index);
                accounts.get(indexOfPresentAccount).setChatHistory(oldChatHistory.replace(toBeDeleteHistory, ""));

                //Deleting the Chat Section
                String oldChatSection = accounts.get(indexOfPresentAccount).getChatSections();
                String toBeDeleteSection;
                if (index == chatSections.size() || 1 == chatSections.size()) {
                    toBeDeleteSection = chatSections.get(index);
                } else {
                    toBeDeleteSection = chatSections.get(index)+",";
                }
                accounts.get(indexOfPresentAccount).setChatSection(oldChatSection.replace(toBeDeleteSection, ""));

                accounts.get(indexOfPresentAccount).setCurrentChatSection(-1);
                initialize();
            });

            label.setOnMouseClicked((MouseEvent event) -> {
                accounts.get(indexOfPresentAccount).setCurrentChatSection(indexOfCurrentSection);
                displayChatSections(chatSections, chatHistory, chats);
                displayChatHistory(chatSections, chatHistory, chats);
            });
            

            Pane pane = new Pane();
            pane.getChildren().addAll(label, image);

            chatSectionPanel.getChildren().addAll(pane);
            chatSectionPanel.setMargin(pane, new Insets(0, 0, 10, 0));
        }
        System.out.println("Done displaying chat Sections");
    }

    public void displayChatHistory(ArrayList<String> chatSections, ArrayList<String> chatHistory, ArrayList<String> chats) {
        //Adding elements to the UI with the chats
        if (accounts.get(indexOfPresentAccount).getCurrentChatSection() != -1) {

            chatBoard.getChildren().clear();
            //Parsing the chatHistory and split it then adding it to the 'chats' array
            chats = new ArrayList<String>();
            String getStringFromChatHistory = chatHistory.get(accounts.get(indexOfPresentAccount).getCurrentChatSection()).replace("User: ", "").replace("ChatGPT: ", "");            
            if (getStringFromChatHistory.length() > 0) {
                String[] chatsSplit = getStringFromChatHistory.split("_SPLIT_");
                for (int i = 0; i < chatsSplit.length; i++) {
                    if (chatsSplit[i].length() > 0) {
                        chats.add(chatsSplit[i]);
                    }
                }

                chatBoardScrollPane.setVisible(true);
                newTab.setVisible(false);

                //Displaying the chats history to the chat board
                for (int i = 0; i < chats.size(); i += 2) {
                    //User message
                    Text userText = new Text();
                    userText.setText(chats.get(i));
                    if (userText.getText().length() <= 50) {
                        userText.setWrappingWidth(userText.getText().length()*10);
                    } else {
                        userText.setWrappingWidth(540);
                    }
                    userText.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                    userText.getStyleClass().add("userMessageText");
        
                    Pane pane1 = new Pane();
                    pane1.getChildren().addAll(userText);
                    pane1.getStyleClass().add("userMessagePane");
        
                    Pane pane2 = new Pane();
                    pane2.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    pane2.getChildren().addAll(pane1);
        
                    chatBoard.getChildren().addAll(pane2);
        
                    //ChatGPT message
                    Text chatGPTText = new Text();
                    chatGPTText.setText(chats.get(i+1));
                    chatGPTText.getStyleClass().add("chatgptMessageText");
                    chatGPTText.setWrappingWidth(540);
        
                    Image imagee = new Image(getClass().getResourceAsStream("/chatgptclone/img/logo-inCircle.png"));
                    ImageView chatGPTImage = new ImageView(imagee);
                    chatGPTImage.getStyleClass().add("chatgptMessageLogo");
                    chatGPTImage.setFitHeight(50);
                    chatGPTImage.setFitWidth(50);
        
                    Pane pane3 = new Pane();
                    pane3.getStyleClass().add("chatgptMessageLogo");
                    pane3.getChildren().addAll(chatGPTText, chatGPTImage);
        
                    chatBoard.getChildren().addAll(pane3);
                    numOfSections = chatSections.size();
                } 
                } else {
                    chatBoardScrollPane.setVisible(false);
                    newTab.setVisible(true);
                }
        } else {
            chatBoardScrollPane.setVisible(false);
            newTab.setVisible(true);
        }
        System.out.println("Done displaying chat History");
    }

    @FXML
    void newChat(MouseEvent event) {
        chatBoardScrollPane.setVisible(false);
        newTab.setVisible(true);
        accounts.get(indexOfPresentAccount).setCurrentChatSection(-1);
        displayChatHistory(chatSections, chatHistory, chatSections);
        displayChatSections(chatSections, chatHistory, chatSections);
        chatSections = new ArrayList<String>();
        chatHistory = new ArrayList<String>();
        chats = new ArrayList<String>();
    }

    @FXML
    void sendMessage(ActionEvent event) {
        prompt(textField.getText());
        textField.setText("");
        saveData();
        initialize();
    }

    public static void suggestPrompts(Text idea, Text knowledge, Text shopping, Text programming) {
        try {
            idea.setText(AIChat.suggestPrompt("ideas/activities"));
            knowledge.setText(AIChat.suggestPrompt("knowledge"));
            shopping.setText(AIChat.suggestPrompt("shopping"));
            programming.setText(AIChat.suggestPrompt("programming"));
        } catch (Exception e) {
            idea.setText("N/A");
            knowledge.setText("N/A");
            shopping.setText("N/A");
            programming.setText("N/A");
        }
    }

    public static void prompt(String userText) {
        String userMessage = userText;
        String aiMessage = AIChat.chat(userText);
        String compiledMessage = "_SPLIT_User: "+userMessage+"_SPLIT_ChatGPT: "+aiMessage+"_SPLIT_";
        System.out.println(accounts.get(indexOfPresentAccount).getCurrentChatSection());
            //If new section
            if (accounts.get(indexOfPresentAccount).getCurrentChatSection() == -1) {
                newTitleSection = AIChat.makeTitle(userText);
                String getOldChatHistory = accounts.get(indexOfPresentAccount).getChatHistory();
                String newChatHistory = getOldChatHistory+"[|]"+newTitleSection+"|";
                String getOldChatSection = accounts.get(indexOfPresentAccount).getChatSections();
                accounts.get(indexOfPresentAccount).setChatHistory(newChatHistory+compiledMessage);
                accounts.get(indexOfPresentAccount).setCurrentChatSection(chatSections.size());
                if (getOldChatSection.length() !=  0) {
                    accounts.get(indexOfPresentAccount).setChatSection(getOldChatSection+","+newTitleSection);
                } else {
                    accounts.get(indexOfPresentAccount).setChatSection(newTitleSection);
                }
                accounts.get(indexOfPresentAccount).setCurrentChatSection(chatSections.size());
            } else { //If it was in current chat section
                int indexofCurrentChat = accounts.get(indexOfPresentAccount).getCurrentChatSection();
                String presentChatHistory = chatHistory.get(indexofCurrentChat);
                chatHistory.set(indexofCurrentChat, presentChatHistory+compiledMessage);
                String oldChatHistory = accounts.get(indexOfPresentAccount).getChatHistory();
                String oldCurrentChatHistory = "[|]"+chatSections.get(indexofCurrentChat)+"|"+presentChatHistory;
                String newChatHistory = "[|]"+chatSections.get(indexofCurrentChat)+"|"+presentChatHistory+compiledMessage;

                accounts.get(indexOfPresentAccount).setChatHistory(oldChatHistory.replace(oldCurrentChatHistory, newChatHistory));
            }
    }
    @FXML
    void promptIdea(MouseEvent event) {
        prompt(suggestPromptIdea.getText());
    }

    @FXML
    void promptKnowledge(MouseEvent event) {
        prompt(suggestPromptKnowledge.getText());
    }

    @FXML
    void promptProgramming(MouseEvent event) {
        prompt(suggestPromptProgramming.getText());
    }

    @FXML
    void promptShopping(MouseEvent event) {
        prompt(suggestPromptShopping.getText());
    }
}
