package chatgptclone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class resetPasswordController extends MainSceneController{
    @FXML
    private TextField emailField;
    @FXML
    private Text errorEmail;
    
    @FXML
    void continueBtn(ActionEvent event) {
        if (!emailField.getText().toString().contains("@gmail.com") || emailField.getText().length() == 0) {
            emailField.setStyle(emailField.getStyle()+"; -fx-border-color: red");
            errorEmail.setVisible(true);
        } else {
        }
    }

    @FXML
    void gotoLoginUsername(MouseEvent event) throws Exception {
        App.setRoot("loginUsername");
    }
}
