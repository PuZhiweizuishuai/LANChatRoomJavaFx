package ChatMessage.Login;

import ChatMessage.Main.Main;
import ChatMessage.SignUp.SignUp;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mycontrol.popup.PopUpUI;


/**
 * @author Pu Zhiwei
 * */
public class LoginControl implements Initializable {
    @FXML
    private AnchorPane rootBox;

    @FXML
    private TextField userName;

    @FXML
    private  PasswordField userPassword;


    @Override
    public void initialize(URL location, ResourceBundle resource) {
        //TODO
    }

    /**
     * 判断密码或用户名是否为空
     * */
    private boolean checkUpNameAndPwd(String name, String passworad) {
        if(name.equals("")|| passworad.equals(""))
            return false;
        return true;
    }


    /**
     * 点击登陆按钮后的事件
     * */
    @FXML
    public void clickLoginButton(ActionEvent event) {
        String name = userName.getText();
        String pwd = userPassword.getText();
        if(checkUpNameAndPwd(name, pwd)) {
            Main main = new Main();
            try {
                Stage thisStage = (Stage) rootBox.getScene().getWindow();
                thisStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new PopUpUI("提示:", "请输入用户名和密码!");
        }
        //TODO
    }

    /**
     * 点击注册按钮后的事件
     * */
    @FXML
    public void clickSignUpButton(ActionEvent event) {
        new SignUp();
        try {
            Stage thisStage = (Stage) rootBox.getScene().getWindow();
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
        Stage stage = (Stage) rootBox.getScene().getWindow();
        // 最小化到任务栏
        stage.setIconified(true);
    }
}
