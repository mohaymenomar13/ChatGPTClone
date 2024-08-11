package chatgptclone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class loginUsernamePassController extends MainSceneController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField usernameField;
    @FXML
    private ToggleButton toggleBtnCA;
    @FXML
    private Text errorPass;

    public void initialize()  {
        //pfp2L.setText(accounts[0].getEmailAddress().substring(0, 2).toUpperCase());
        usernameField.setText(accounts.get(indexOfPresentAccount).getEmailAddress().toLowerCase());
    }
    
    String password = "";
    boolean getTextPass = false;
    @FXML
    void continueBtn(ActionEvent event) throws Exception {
        if (getTextPass) {
            password = passwordField.getText();
        } else {
            password = passwordField.getText();
        }

        boolean checkAccount = MainSceneController.checkPassword(password);

        if (checkAccount) {
            App.setRoot("mainPage");
        } else {
            errorPass.setVisible(true);
            passwordField.setStyle(passwordField.getStyle()+"; -fx-border-color: red");
        }
    }

    @FXML
    void editBtn(MouseEvent event) throws Exception {
        App.setRoot("loginUsername");
    }

    @FXML
    void forgotPass(MouseEvent event) throws Exception {
        App.setRoot("resetPassword");
    }

    @FXML
    void gotoCreateAccount(MouseEvent event) throws Exception {
        App.setRoot("createAccount");
    }

    @FXML
    void togglePassword(ActionEvent event) {
        if (toggleBtnCA.isSelected()) {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordField.setVisible(false);
            getTextPass = true;
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
            getTextPass = false;
        }
    }

}
