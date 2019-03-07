package ChatMessage.SignUp;

import ChatMessage.Login.LoginMain;
import ChatMessage.communication.Communication;
import ChatMessage.user.SaveUser;
import ChatMessage.user.UserInformation;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import mycontrol.popup.PopUpUI;


public class SignUpControl {
    private static SignUpControl instance;
    Communication comm;
    @FXML
    private AnchorPane signInStage;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userName;

    @FXML
    private TextField userPassword;

    private static boolean isSingUpSuecces = false;

    public SignUpControl() {
        instance = this;
    }

    public static SignUpControl getInstance() {
        return instance;
    }

    @FXML
    public void clickSignInButtion(ActionEvent event) {
        LoginMain loginMain = new LoginMain();
        if(checkUserInput()) {
            UserInformation user = new UserInformation(userEmail.getText(),userName.getText(),userPassword.getText());
            try {
                SaveUser.userSerialize(user);
                if(isSingUpSuecces) {
                    new PopUpUI("提示：", "注册成功！");
                    loginMain.showWindow();
                    Stage thisStage = (Stage) signInStage.getScene().getWindow();
                    thisStage.close();
                } else {
                    new PopUpUI("提示：","该用户名已存在！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new PopUpUI("提示：", "请填写正确的信息后注册！");
        }
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
        //TODO
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

    private boolean checkUserInput() {
        if(userEmail.getText().equals("") || userName.getText().equals("") || userPassword.getText().equals("")) {
            return false;
        }
        return true;
    }

    public void setIsSingUpSuecces(boolean isSingUpSuecces) {
        SignUpControl.isSingUpSuecces = isSingUpSuecces;
    }

}
