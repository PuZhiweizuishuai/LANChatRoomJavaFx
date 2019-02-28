package ChatMessage.SignIn;

import ChatMessage.Login.LoginMain;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;


public class SignInControl {
    @FXML
    private AnchorPane signInStage;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userName;

    @FXML
    private TextField userPassword;

    @FXML
    public void clickSignInButtion(ActionEvent event) {
        //TODO
    }

    @FXML
    public void backButtion(ActionEvent event) {
        LoginMain loginMain = new LoginMain();
        try {
            loginMain.showWindow();
            Stage thisStage = (Stage) signInStage.getScene().getWindow();
            thisStage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 关闭窗口
     * */
    @FXML
    public void close(ActionEvent event) {
        System.exit(0);
    }

    /**
     * 最小化
     * */
    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) signInStage.getScene().getWindow();
        // 最小化到任务栏
        stage.setIconified(true);
    }
}
