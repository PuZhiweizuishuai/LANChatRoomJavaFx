package ChatMessage.Setting;

import ChatMessage.user.SaveSetting;
import ChatMessage.user.SaveUser;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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
        namelable.setText(SaveUser.getLoginUserName());
        Image head = new Image(SaveUser.getPicPath());
        headImage.setImage(head);
    }


    public void clickUserMessageButton(ActionEvent event) {
        message.setVisible(true);
        aboutMePane.setVisible(false);
        settingPane.setVisible(false);

    }

    public void clickSettingButton(ActionEvent event) {
        settingPane.setVisible(true);
        message.setVisible(false);
        aboutMePane.setVisible(false);
    }

    public void clickAboutMeButton(ActionEvent event) {
        aboutMePane.setVisible(true);
        settingPane.setVisible(false);
        message.setVisible(false);
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

}
