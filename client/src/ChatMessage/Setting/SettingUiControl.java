package ChatMessage.Setting;

import ChatMessage.Main.MainUIControl;
import ChatMessage.communication.Communication;
import ChatMessage.user.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mycontrol.popup.PopUpUI;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 设置界面控制模块
 * @author Pu Zhiwei
 * */
public class SettingUiControl implements Initializable {
    @FXML
    private AnchorPane rootBox;

    @FXML
    private JFXButton userMessageButton;

    @FXML
    private JFXButton settingButton;

    @FXML
    private JFXButton aboutMeButton;

    @FXML
    private AnchorPane message;

    @FXML
    private AnchorPane settingPane;

    @FXML
    private AnchorPane aboutMePane;

    @FXML
    private ImageView headImage;

    @FXML
    private Label namelable;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private JFXButton changePwdBtn;

    @FXML
    private JFXToggleButton onLineButton;

    @FXML
    private JFXToggleButton promptToneButton;

    private String newPwd;
    private String oldPwd;
    private MainUIControl mainUIControl;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        if(SaveSetting.isOnLinePrompt) {
            onLineButton.setSelected(true);
            onLineButton.setText("开");
        } else {
            onLineButton.setSelected(false);
            onLineButton.setText("关");
        }
        if(SaveSetting.isPromptSound) {
            promptToneButton.setSelected(true);
            promptToneButton.setText("开");
        } else {
            promptToneButton.setSelected(false);
            promptToneButton.setText("关");
        }
        Image head = new Image(SaveUser.getPicPath());
        headImage.setImage(head);
    }

    public void setNamelable(String name) {
        namelable.setText("用户名："+name);
    }

    public void clickUserMessageButton(ActionEvent event) {
        userMessageButton.setStyle("-fx-background-color: #3fcb48;");
        settingButton.setStyle("-fx-background-color: #f4f4f4");
        aboutMeButton.setStyle("-fx-background-color: #f4f4f4");
        message.setVisible(true);
        aboutMePane.setVisible(false);
        settingPane.setVisible(false);

    }

    public void clickSettingButton(ActionEvent event) {
        settingButton.setStyle("-fx-background-color: #3fcb48;");
        userMessageButton.setStyle("-fx-background-color: #f4f4f4");
        aboutMeButton.setStyle("-fx-background-color: #f4f4f4");
        settingPane.setVisible(true);
        message.setVisible(false);
        aboutMePane.setVisible(false);
    }

    public void clickAboutMeButton(ActionEvent event) {
        aboutMeButton.setStyle("-fx-background-color: #3fcb48;");
        userMessageButton.setStyle("-fx-background-color: #f4f4f4");
        settingButton.setStyle("-fx-background-color: #f4f4f4");
        aboutMePane.setVisible(true);
        settingPane.setVisible(false);
        message.setVisible(false);
    }


    public void setMainUIControl(MainUIControl mainUIControl) {
        this.mainUIControl = mainUIControl;
    }

    public void clickOnLine() {
        if(onLineButton.isSelected()) {
            onLineButton.setText("开");
            SaveSetting.isOnLinePrompt = true;
        } else {
            SaveSetting.isOnLinePrompt = false;
            onLineButton.setText("关");
        }
    }


    public void clickpromptTone() {
        if(promptToneButton.isSelected()) {
            SaveSetting.isPromptSound = true;
            promptToneButton.setText("开");
        } else {
            SaveSetting.isPromptSound = false;
            promptToneButton.setText("关");
        }
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) rootBox.getScene().getWindow();
        stage.close();
    }

    public void changePwd() {
        newPwd = newPassword.getText();
        oldPwd = oldPassword.getText();
        if(!newPwd.equals("") && !oldPwd.equals("")) {
            Message message = new Message(SaveUser.getLoginUserName(), oldPwd, MessageType.CHANGEPWD);
            message.setPassword(newPwd);
            mainUIControl.chengePwds(message);
        } else {
            new PopUpUI("提示：","请输出密码！");

        }
    }

    public void clickGithub() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/PuZhiweizuishuai"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickWeibo() {
        try {
            Desktop.getDesktop().browse(new URI("https://weibo.com/puzhiwei"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
