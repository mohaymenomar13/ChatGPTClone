package chatgptclone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class createAccountController extends MainSceneController {
    @FXML
    private TextField emailAddressCA;
    @FXML
    private PasswordField passwordFieldCA;
    @FXML
    private TextField passwordTextFieldCA;
    @FXML
    private Text errorEmail;
    @FXML
    private ToggleButton toggleBtnCA;

    String password = "";
    boolean getTextPass = false;

    @FXML
    void continueBtnCA(ActionEvent event) throws Exception{

        if (getTextPass) {
            password = passwordTextFieldCA.getText();
        } else {
            password = passwordFieldCA.getText();
        }

        if (emailAddressCA.getText().length() == 0 || !emailAddressCA.getText().toString().contains("@gmail.com")) {
            emailAddressCA.setStyle(emailAddressCA.getStyle()+"; -fx-border-color: red;");
            errorEmail.setVisible(true);
        } else {
            emailAddressCA.setStyle(emailAddressCA.getStyle()+"; -fx-border-color: darkgreen;");
            errorEmail.setVisible(false);

            if (password.length() == 0) {
                errorEmail.setText("*Please enter a password.");
                passwordFieldCA.setStyle(passwordFieldCA.getStyle() + " -fx-border-color: red;");
                passwordTextFieldCA.setStyle(passwordTextFieldCA.getStyle() + " -fx-border-color: red;");
            } else {
                passwordFieldCA.setStyle(passwordFieldCA.getStyle() + " -fx-border-color: darkgreen;");
                passwordTextFieldCA.setStyle(passwordTextFieldCA.getStyle() + " -fx-border-color: darkgreen;");
    
                boolean createdAccount = MainSceneController.createAccount(emailAddressCA.getText(), password);
                if (!createdAccount) {
                    errorEmail.setText("*The email is already registered.");
                    errorEmail.setVisible(true);
                } else {
                    App.setRoot("mainPage");
                }
            }

        }
        
    }

    @FXML
    void togglePassword(ActionEvent event) {
        if (toggleBtnCA.isSelected()) {
            passwordTextFieldCA.setText(passwordFieldCA.getText());
            passwordTextFieldCA.setVisible(true);
            passwordFieldCA.setVisible(false);
            getTextPass = true;
        } else {
            passwordFieldCA.setText(passwordTextFieldCA.getText());
            passwordFieldCA.setVisible(true);
            passwordTextFieldCA.setVisible(false);
            getTextPass = false;
        }
    }

    @FXML
    void gotoLoginUsername(MouseEvent event) throws Exception {
        App.setRoot("loginUsername");
    }
}
