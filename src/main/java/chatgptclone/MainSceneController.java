package chatgptclone;

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainSceneController extends App {
    static ArrayList<Account> accounts = new ArrayList<Account>();
    static List<Map<String, String>> data = new ArrayList<>();
    static int indexOfPresentAccount = -1;
    static int numOfSections = -1;

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    public static void loadData() {
        System.out.println("Loading data...");
        String dataContent = getDataContent();

        if (dataContent.length() > 3) {
            dataContent = dataContent.substring(1, dataContent.length() - 1);
            dataContent = dataContent.replace("{", "").replace("}", "");
            String[] dataArray = dataContent.split(", accountInfo");
            for (String dataa : dataArray) {
        
                String accountSplit = dataa.substring(dataa.indexOf("="), dataa.indexOf(", chatHistory"));
                String[] accountInfo = accountSplit.substring(1).split(",");
                String chatHistory = dataa.substring(dataa.indexOf("chatHistory=")+12, dataa.indexOf(", chatSections="));
                String chatSections = dataa.substring(dataa.indexOf("chatSections=")+13);

                accounts.add(new Account(accountInfo[0], accountInfo[1], chatHistory, chatSections));
            }
            System.out.println("Finished loading data. \nCurrently Account registered: "+accounts.size());
        } else {
            System.out.println("The data content is empty.");
        }
    }


    public static void saveData() {
        System.out.println("Saving data...");
        List<Map<String, String>> dataMap = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            Map<String, String> accountInfoMap = new HashMap<>();
            //for accountInfo
            accountInfoMap.put("accountInfo", accounts.get(i).getEmailAddress()+","+accounts.get(i).getPassword());
            accountInfoMap.put("chatHistory", accounts.get(i).getChatHistory());
            accountInfoMap.put("chatSections",accounts.get(i).getChatSections());
            dataMap.add(accountInfoMap);
        }

        data = dataMap;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            writer.write(data.toString());
        } catch (Exception e) {
            System.out.println("Something went wrong saving the data.");
        }
        System.out.println("The data successfully saved");
    }

    public static String getDataContent() {
        String theContent = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            while((theContent = reader.readLine()) != null) {
                return theContent;
            };
        } catch (Exception e) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt."))) {
                writer.write("[]");
            } catch (Exception er) {
                System.out.println("Something went wrong getting Data Content.");
            }
        }
        return theContent;
    }

    public static boolean createAccount(String emailAddress, String password) {
        for (int i = 0; i < accounts.size(); i++) {
            if (emailAddress.equalsIgnoreCase(accounts.get(i).getEmailAddress())) {
                return false;
            }
        }
        //accounts[activeAccounts] = new Account(emailAddress, password, "", "");
        accounts.add(new Account(emailAddress, password, "", ""));
        indexOfPresentAccount = accounts.size() - 1;
        saveData();
        return true;
    }

    public static boolean checkEmail(String emailAddress) {
        for (int i = 0; i < accounts.size(); i++) {
            if (emailAddress.equalsIgnoreCase(accounts.get(i).getEmailAddress())) {
                indexOfPresentAccount = i;
                return true;
            }
        }
        return false;
    }

    public static boolean checkPassword(String password) {
        if (password.equals(accounts.get(indexOfPresentAccount).getPassword())){
            return true;
        }
        return false;
    }

}