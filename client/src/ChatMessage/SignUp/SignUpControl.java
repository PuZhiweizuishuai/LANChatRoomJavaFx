package ChatMessage.SignUp;

import ChatMessage.Login.LoginMain;
import ChatMessage.Main.MainUIControl;
import ChatMessage.communication.Communication;
import ChatMessage.user.MessageType;
import ChatMessage.user.SaveUser;
import ChatMessage.user.ServerIP;
import ChatMessage.user.UserInformation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import mycontrol.popup.PopUpUI;


public class SignUpControl {
    @FXML
    private AnchorPane signInStage;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userName;

    @FXML
    private TextField userPassword;

    private static boolean isSingUpSuecces = false;
    private LoginMain loginMain = new LoginMain();
    private Communication communication;
    private MainUIControl mainUIControl;
    private SignUpControl signUpControl;


    public SignUpControl() {

    }

    public void set(MainUIControl mainUIControl, SignUpControl signUpControl) {
        this.mainUIControl = mainUIControl;
        this.signUpControl = signUpControl;
    }






    @FXML
    public void clickSignInButtion(ActionEvent event) {
        if(checkUserInput()) {
            UserInformation user = new UserInformation(userEmail.getText(),userName.getText(),userPassword.getText());
            try {
                SaveUser.userSerialize(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            communication = new Communication(ServerIP.IP,ServerIP.port,userName.getText(),"/resources/images/defaultHead.png", mainUIControl, this);
            communication.setUserPassword(userPassword.getText());
            communication.setEmail(userEmail.getText());
            communication.setMessageType(MessageType.SIGNUP);
            Thread x = new Thread(communication);
            x.start();
        } else {
            new PopUpUI("提示：", "请填写正确的信息后注册！");
        }
    }

    @FXML
    public void backButtion(ActionEvent event) {
        loadLogin();
    }

    /**
     * 关闭窗口
     * */
    @FXML
    public void close(ActionEvent event) {
        //TODO
        Platform.exit();
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
        String email = userEmail.getText();
        String name = userName.getText();
        String pwd = userPassword.getText();

        if(pwd.equals("") || name.equals("") || email.equals("")) {
            new PopUpUI("提示：", "请填写正确的数据");
            return false;
        }
        if(!email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            new PopUpUI("提示：", "邮箱格式错误！");
            return false;
        }
        if(userName.getLength() >= 10) {
            new PopUpUI("提示：", "用户名超过10位！");
            return false;
        }
        return true;
    }

    public void loadLogin() {
        Platform.runLater(()-> {
            try {
                new PopUpUI("提示：", "注册成功！");
                loginMain.showWindow();
                Stage thisStage = (Stage) signInStage.getScene().getWindow();
                thisStage.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
