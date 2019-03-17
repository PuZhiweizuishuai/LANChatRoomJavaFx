package SettingServer;

import ChatMessage.Login.LoginMain;
import ChatMessage.user.SaveUser;
import ChatMessage.user.ServerIP;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mycontrol.popup.PopUpUI;

import java.util.regex.Pattern;

public class SettingServerIPCoontroll {
    @FXML
    private AnchorPane rootBox;

    @FXML
    private TextField ServerIP;

    @FXML
    private TextField ServerPort;

    @FXML
    private TextField ServerTime;

    private boolean isIP(String ip) {
        String regex = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(ip).matches();
    }

    private boolean isPort(String port) {
        String regex =  "^[0-9]*[1-9][0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(port).matches();
    }


    public void save() {
        String ip = ServerIP.getText();
        String port = ServerPort.getText();
        String time = ServerTime.getText();
        if(ip.isEmpty() || port.isEmpty() || time.isEmpty()) {
            new PopUpUI("提示：","请输入数据！");
            return;
        }

        if(isIP(ip) && isPort(port) && isPort(time)) {
            ChatMessage.user.ServerIP.IP = ip;
            ChatMessage.user.ServerIP.port = new Integer(port);
            ChatMessage.user.ServerIP.timeout = new Integer(time);
            try {
                SaveUser.saveServerIP(new ServerIP());
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new PopUpUI("提示：","您的输入有误！");
        }

    }

    public void saveAndOpen() {
        String ip = ServerIP.getText();
        String port = ServerPort.getText();
        String time = ServerTime.getText();
        if(ip.isEmpty() || port.isEmpty() || time.isEmpty()) {
            new PopUpUI("提示：","请输入数据！");
            return;
        }

        if(isIP(ip) && isPort(port) && isPort(time)) {
            ChatMessage.user.ServerIP.IP = ip;
            ChatMessage.user.ServerIP.port = new Integer(port);
            ChatMessage.user.ServerIP.timeout = new Integer(time);
            try {
                SaveUser.saveServerIP(new ServerIP());
                LoginMain loginMain = new LoginMain();
                loginMain.showWindow();
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new PopUpUI("提示：","您的输入有误！");
        }
    }


    public void close() {
        Stage stage = (Stage) rootBox.getScene().getWindow();
        stage.close();
    }

    public void min() {
        Stage stage = (Stage) rootBox.getScene().getWindow();
        // 最小化到任务栏
        stage.setIconified(true);
    }

}
