package ChatMessage.history;

import ChatMessage.Main.MainUIControl;
import ChatMessage.user.Message;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mycontrol.popup.PopUpUI;

public class HistoryControl {
    private MainUIControl mainUIControl;
    @FXML
    private AnchorPane rootBox;

    @FXML
    private TextField getNameField;

    @FXML
    private TextArea showChatHistoryArea;

    @FXML
    private JFXButton movebutton;

    public void setMainUIControl(MainUIControl mainUIControl) {
        this.mainUIControl = mainUIControl;
    }

    public void setGetNameField(String name) {
        getNameField.setText(name);
    }

    public JFXButton getMovebutton() {
        return movebutton;
    }


    public void sendLookUpHistory() {
        String lookUpName = getNameField.getText();
        if(!lookUpName.equals("")) {
            new PopUpUI("提示：","待更新！");
        } else {
            new PopUpUI("提示：","请输入要查询的用户名!");
        }
    }

    public void show(Message message) {
        showChatHistoryArea.setText(showChatHistoryArea.getText() + "\n" + message.getMessage());
    }


    public void closeThisWindow() {
        Stage stage = (Stage) rootBox.getScene().getWindow();
        stage.close();
    }
}
