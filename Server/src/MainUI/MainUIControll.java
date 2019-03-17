package MainUI;

import DB.DBControl;
import DB.DbConnect;
import Server.ChatServer;
import Server.SaveServerMessage;
import Server.ServerMessage;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import popup.PopUpUI;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MainUIControll implements Initializable {
    @FXML
    private AnchorPane rootBox;

    @FXML
    private JFXButton moveButton;

    @FXML
    private TextField mysqlIP;

    @FXML
    private TextField mysqlPort;

    @FXML
    private JFXButton mysqlOK;

    @FXML
    private JFXButton bulidMysql;

    @FXML
    private TextField serverPort;

    @FXML
    private JFXButton serverOK;

    @FXML
    private TextField mysqlName;

    @FXML
    private PasswordField mysqlPwd;

    @FXML
    private JFXButton startServer;



    @Override
    public void initialize(URL location, ResourceBundle resource) {
        //ChatServer.setMainUIControll(this);
    }

    public JFXButton getMoveButton() {
        return moveButton;
    }




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

    public void setMysqlAndCheck() {
        String ip = mysqlIP.getText();
        String port = mysqlPort.getText();
        String name = mysqlName.getText();
        String pwd = mysqlPwd.getText();
        if(ip.isEmpty() || port.isEmpty() || name.isEmpty() || pwd.isEmpty()) {
            new PopUpUI("提示：","请输入内容！");
            return;
        }

        if(isIP(ip) && isPort(port)) {
            ServerMessage.MYSSQL = ip;
            ServerMessage.MYSQLPORT = port;
            ServerMessage.mysqlName = name;
            ServerMessage.mysqlPassword = pwd;
            if(DbConnect.connect()) {
                mysqlIP.setDisable(true);
                mysqlPort.setDisable(true);
                mysqlName.setDisable(true);
                mysqlPwd.setDisable(true);
                mysqlOK.setDisable(true);
                bulidMysql.setDisable(false);
                serverPort.setDisable(false);
                serverOK.setDisable(false);
            } else {
                new PopUpUI("提示：","数据库连接失败,请重试");
            }


        } else {
            new PopUpUI("提示：","请输正确的IP格式！");
        }
    }


    public void foundMysql() {
        if(DbConnect.foundChatMessage()) {
            new PopUpUI("提示：","已存在，无需创建");
            bulidMysql.setDisable(true);
        } else {
            if(DbConnect.fount()) {
                new PopUpUI("提示：","创建成功！");
                bulidMysql.setDisable(true);
            } else {
                new PopUpUI("提示：","创建失败！请重试");
            }
        }
    }

    public void setServerPort() {
        String port = serverPort.getText();
        if(!port.isEmpty() && isPort(port)) {
            ServerMessage.PORT = new Integer(port);
            serverPort.setDisable(true);
            serverOK.setDisable(true);
            startServer.setDisable(false);
            bulidMysql.setDisable(true);
        } else {
            new PopUpUI("提示：","请输正确的数据！");
        }
    }

    public void clickStartServer() {
        try {
            SaveServerMessage.save(new ServerMessage());
            Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /k start java -jar Server.jar");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void close() {
        Platform.exit();
        System.exit(0);
    }

    public void min() {
        Stage stage = (Stage) rootBox.getScene().getWindow();
        // 最小化到任务栏

        stage.setIconified(true);
    }
}
