package chatgptclone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class loginUsernameController extends MainSceneController {

    @FXML
    private TextField emailAddressCA;

    @FXML
    private Text errorEmail;

    public void initialize() {

        System.out.println(indexOfPresentAccount);
        if (indexOfPresentAccount != -1) {
            emailAddressCA.setText(accounts.get(indexOfPresentAccount).getEmailAddress().toLowerCase());
        } else {
            System.out.println("No active account yet");
        }   
    }

    @FXML
    void continueBtn(ActionEvent event) throws Exception {
        boolean checkEmail = MainSceneController.checkEmail(emailAddressCA.getText());
        System.out.println(checkEmail);
        if (emailAddressCA.getText().length() != 0 && checkEmail) {
            App.setRoot("loginUsernamePass");
        } else {
            emailAddressCA.setStyle(emailAddressCA.getStyle()+"; -fx-border-color: red;");
            errorEmail.setVisible(true);
        }
    }

    @FXML
    void gotoCreateAccount(MouseEvent event) throws Exception {
        App.setRoot("createAccount");
    }

}
