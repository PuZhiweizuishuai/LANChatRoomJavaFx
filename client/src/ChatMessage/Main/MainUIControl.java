package ChatMessage.Main;

import ChatMessage.Login.DragUtil;
import ChatMessage.Setting.SettingUIMain;
import ChatMessage.Setting.SettingUiControl;
import ChatMessage.communication.Communication;
import ChatMessage.history.HistoryControl;
import ChatMessage.history.HistoryMain;
import ChatMessage.user.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.traynotifications.animations.AnimationType;
import com.traynotifications.notification.TrayNotification;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import mycontrol.chatbox.MyChatBox;
import mycontrol.chatbox.OtherChatBox;
import mycontrol.popup.PopUpUI;
import mycontrol.userlist.UserListUI;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 * 聊天主界面控制
 * @author PuZhiwei
 * */
public class MainUIControl implements Initializable {
    /**
     * 按钮
     */
    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXButton minimizeButton;

    @FXML
    private JFXButton settingButton;

    /**
     * 外部界面
     */
    @FXML
    private GridPane rootPane;

    @FXML
    private ColumnConstraints rightCol;

    @FXML
    private ColumnConstraints leftCol;

    @FXML
    private RowConstraints topRow;

    @FXML
    private RowConstraints centreRow;

    @FXML
    private RowConstraints downRow;

    /**
     * 文本输入界面
     */
    @FXML
    private ColumnConstraints inputChatLeftCol;

    @FXML
    private ColumnConstraints inputChatCentreCol;

    @FXML
    private ColumnConstraints inputChatRightCol;

    @FXML
    private RowConstraints inputChatTopRow;

    @FXML
    private RowConstraints inputChatCentreRow;

    @FXML
    private RowConstraints inputChatDownRow;

    @FXML
    private AnchorPane inputChatAnchor;

    @FXML
    private JFXButton sendButton;

    @FXML
    private TextArea inputText;

    /**
     * 消息显示界面
     */
    @FXML
    private ColumnConstraints showMessagesLeftCol;

    @FXML
    private ColumnConstraints showMessagesCentreCol;

    @FXML
    private ColumnConstraints showMessagesRightCol;

    @FXML
    private ListView chatBoxList;

    @FXML
    private Label nameLabelTop;

    @FXML
    private ImageView headImageTop;

    /**
     * 时间
     * */
    private  Date lastTime;

    /**
     * 左侧联系人
     */
    @FXML
    private Label nowUserNumber;

    @FXML
    private JFXListView contactsList;

    private HashMap<String, UserListUI> contactsHashMap = new HashMap<>();
    private HashMap<String, Integer> contactMessageNumber = new HashMap<>();


    /**
     * 保存个人的消息记录
     * */
    private HashMap<String, LinkedList<Message>> messageLog = new HashMap<>();

    private LinkedList<Message> groupMessage = new LinkedList<>();

    private String ChatObject = "群聊";

    /**
     * 左侧信息
     * */
    @FXML
    private ImageView lefttMyHead;
    @FXML
    private Label leftNameLabel;

    /**
     * 个人信息
     * */
    private String headImagePath;
    private String userName;

    /**
     * socket网络发送消息
     */
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;


    /**
     * 获取屏幕大小用于自适应窗口
     * */
    private Dimension sceneSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double sceneWidth = sceneSize.width * 0.8;
    private double sceneHeight = sceneSize.height * 0.8;

    private SettingUIMain settingUIMain;
    private HistoryMain historyMain;
    private SettingUiControl settingUiControl;
    private HistoryControl historyControl;
    private Stage stageSetting = new Stage();
    private Stage stageHistory = new Stage();
    private Parent settingRoot;
    private FXMLLoader fxmlLoader;
    private Parent historyRoot;
    private FXMLLoader historyLoader;
    /**
     * 初始化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        fxmlLoader = new FXMLLoader(getClass().getResource("/resources/fxml/SettingUI.fxml"));
        historyLoader = new FXMLLoader(getClass().getResource("/resources/fxml/HistoryUI.fxml"));
        try {
            settingRoot = fxmlLoader.load();
            historyRoot = historyLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 加载与注入控制器
        settingUiControl= fxmlLoader.getController();
        settingUiControl.setMainUIControl(this);
        historyControl = historyLoader.getController();
        historyControl.setMainUIControl(this);
        historyMain = new HistoryMain(historyLoader, historyRoot, historyControl);
        settingUIMain = new SettingUIMain(fxmlLoader, settingRoot);

        // 外部界面大小
        rootPane.setPrefWidth(sceneWidth);
        rootPane.setPrefHeight(sceneHeight);
        rightCol.setPrefWidth(sceneWidth * 0.65);
        leftCol.setPrefWidth(sceneWidth - (sceneWidth * 0.65));
        topRow.setPrefHeight(sceneHeight * 0.1);
        centreRow.setPrefHeight(sceneHeight * 0.65);
        downRow.setPrefHeight(sceneHeight * 0.25);

        // 文本输入界面
        double inputSceneWidth = sceneWidth * 0.65;
        double inputSceneHeight = sceneHeight * 0.25;
        inputChatLeftCol.setPrefWidth(inputSceneWidth * 0.05);
        inputChatCentreCol.setPrefWidth(inputSceneWidth * 0.9);
        inputChatRightCol.setPrefWidth(inputSceneWidth * 0.05);

        inputChatTopRow.setPrefHeight(inputSceneHeight * 0.25);
        inputChatCentreRow.setPrefHeight(inputSceneHeight * 0.6);
        inputChatDownRow.setPrefHeight(inputSceneHeight * 0.15);

        inputChatAnchor.setPrefSize(inputSceneWidth * 0.9, inputSceneHeight * 0.6);

        sendButton.setPrefSize(inputSceneWidth * 0.15, inputSceneHeight * 0.3);
        // 自动换行
        inputText.setWrapText(true);
        inputText.setPrefWidth(inputSceneWidth * 0.72);

        // 聊天界面
        showMessagesLeftCol.setPrefWidth(inputSceneWidth * 0.025);
        showMessagesCentreCol.setPrefWidth(inputSceneWidth * 0.95);
        showMessagesRightCol.setPrefWidth(inputSceneWidth * 0.025);

        double buttonHight = (sceneHeight * 0.1) / 2 - 25;
        nameLabelTop.setText("群聊中：");
        headImageTop.setLayoutY(buttonHight - 5);
        headImageTop.setFitWidth(60);
        headImageTop.setFitHeight(60);
        headImageTop.setImage(new Image("/resources/images/GroupChat.png"));

        // 按钮
        closeButton.setLayoutY(buttonHight);
        minimizeButton.setLayoutY(buttonHight);
        settingButton.setLayoutY(buttonHight);

        lefttMyHead.setImage(new Image(SaveUser.getPicPath()));
    }

    public void setLeftNameLabel(String name) {
        leftNameLabel.setText(name);
        settingUiControl.setNamelable(name);
        historyControl.setGetNameField(ChatObject);
    }

    /**
     * 向服务器发送消息
     * 鼠标事件
     */
    @FXML
    private void sendMessages(ActionEvent event) {
        sendMessageToServer();
    }

    /**
     * 发送消息快捷键
     * */
    @FXML
    public void sendMethod(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            sendMessageToServer();
        }
    }

    @FXML
    private void close() {
        Platform.exit();
        System.exit(0);
    }

    public void minimize(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        // 最小化到任务栏
        stage.setIconified(true);
    }
    /**
     * 显示我发出的消息
     * */
    public void showMyMessage(String myMessage) {
        getTime();
        int length = myMessage.length();
        int width;
        if (length <= 2){
            width = 28*3;
        } else if (length <= 28 && length > 2) {
            width = 28 * length;
        } else {
            width = 600;
        }
        int height;
        if (((length / 28) + 1) >= 3) {
            height = ((length / 28) + 1) * 35;
        } else {
            height = ((length / 28) + 1) * 40;
        }
        Platform.runLater(()->{
            MyChatBox myChatBox = new MyChatBox();
            myChatBox.setMessage(myMessage);
            myChatBox.setHeightAndWidth(height,width);
            myChatBox.setHeadImageView(SaveUser.getPicPath());
            chatBoxList.getItems().add(myChatBox);
            inputText.setText("");
        });

    }

    /**
     * 显示收到的消息
     * */
    public void showOtherMessage(Message message) {
        if(message.getName().equals(SaveUser.getLoginUserName())) {
            return ;
        }
        getTime();
        String otherMessage = message.getMessage();
        int length = otherMessage.length();
        int width;
        if(length <= 2) {
            width = 3*28;
        } else if (length <= 28 && length > 2) {
            width = 28 * length;
        } else {
            width = 600;
        }
        int height;
        if (((length / 28) + 1) >= 3) {
            height = ((length / 28) + 1) * 35;
        } else {
            height = ((length / 28) + 1) * 40;
        }
        Platform.runLater(()->{
            OtherChatBox otherChatBox = new OtherChatBox();
            otherChatBox.setMessage(otherMessage);
            otherChatBox.setNameLabel(message.getName());
            otherChatBox.setHeightAndWidth(height, width);
            otherChatBox.setHeadImageView(message.getHeadPicture());
            chatBoxList.setNodeOrientation(NodeOrientation.valueOf("LEFT_TO_RIGHT"));
            chatBoxList.getItems().add(otherChatBox);
        });
    }


    /**
     * 设置联系人右侧消息通知气泡显示消息数
     */
    public void setContactMessageNumber(String name) {
        if(name.equals(ChatObject)) {
            return;
        }
        contactMessageNumber.put(name, contactMessageNumber.get(name) + 1);
    }


    /**
     * 收到消息后设置消息提示并在点击用户名后显示相应的消息
     * */
    public void addOtherMessage(Message message) {
        if(SaveSetting.isPromptSound && !message.getName().equals(SaveUser.getLoginUserName())) {
            sound("/resources/sounds/NewMessage.wav");
        }
        UserListUI userListUI = contactsHashMap.get(message.getName());
        if(ChatObject.equals(message.getName())) {
            showOtherMessage(message);
        } else {
            Platform.runLater(()-> {
                userListUI.setMessageNumber(contactMessageNumber.get(message.getName()).toString());
            });
        }
    }

    /**
     * 显示群消息
     * */
    public void addGroupMessage(Message message) {
        if(SaveSetting.isPromptSound && !message.getName().equals(SaveUser.getLoginUserName())) {
            sound("/resources/sounds/NewMessage.wav");
        }
        UserListUI userListUI = contactsHashMap.get("群聊");
        if(ChatObject.equals("群聊")) {
            showOtherMessage(message);
        } else {
            Platform.runLater(()->{
                contactMessageNumber.put("群聊", contactMessageNumber.get("群聊") + 1);
                userListUI.setMessageNumber(contactMessageNumber.get("群聊").toString());
            });
        }
    }


    /**
     * 获取当前时间,并显示在聊天界面
     * */
    public String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = df.format(date);
        if(lastTime == null || date.getTime() - lastTime.getTime() > 180000) {
            Label timeLabel = new Label(time);
            timeLabel.setFont(new Font("Microsoft YaHei",15));
            chatBoxList.getItems().add(timeLabel);
            lastTime = date;
            return time;
        }
        return time;
    }

    /**
     * 切换联系人
     * */
    public void handleMouseClickContactsList(MouseEvent event) {
        UserListUI userListUI = (UserListUI) contactsList.getSelectionModel().getSelectedItem();
        if(ChatObject.equals(userListUI.getName())) {
            return;
        }
        System.out.println(ChatObject);
        if(userListUI.getName().equals("群聊")) {
            nameLabelTop.setText("群聊中：");
            ChatObject = "群聊";
            historyControl.setGetNameField(ChatObject);
            userListUI.closeMessageNumber();
            switchGroup();
        } else {
            nameLabelTop.setText("与 " + userListUI.getName() + " 聊天中！");
            ChatObject = userListUI.getName();
            historyControl.setGetNameField(ChatObject);
            userListUI.closeMessageNumber();
            switchChatUser();
        }
        contactMessageNumber.put(ChatObject, 0);
        headImageTop.setImage(new Image(userListUI.getImagePath()));
    }


    /**
     * 发送消息
     * */
    private void sendMessageToServer() {
        String myMessage = inputText.getText();

        if(!myMessage.isEmpty()) {
            Message message = new Message(SaveUser.getLoginUserName(),myMessage,MessageType.GROUPSMS);
            message.setTo(ChatObject);
            message.setHeadPicture(SaveUser.getPicPath());
            if(!ChatObject.equals("群聊")) {
                message.setTYPE(MessageType.MSG);
            }
            try {
                Communication.send(message);
                SaveMessage(message);
                saveGroupMessage(message);
            } catch (IOException e) {
               new PopUpUI("提示：", "发送失败，请稍后再试");
               return;
            }
            Platform.runLater(()->{
                showMyMessage(myMessage);
            });
        }
    }

    /**
     * 设置用户列表
     * */
    public void setUserList(Message message) {
        Platform.runLater(()->{
            ArrayList<UserInformation> uifs = message.getUserList();
            int count = uifs.size();
            nowUserNumber.setText("当前用户："+ count +" 人");
            contactsList.getItems().clear();
            contactsHashMap.clear();
            contactMessageNumber.clear();
            UserListUI groupChat = new UserListUI();
            groupChat.setNameLabel("群聊");
            groupChat.setHeadImageView("/resources/images/GroupChat.png");

            contactsHashMap.put("群聊", groupChat);
            contactMessageNumber.put("群聊", 0);

            contactsList.getItems().add(groupChat);

            for(UserInformation uif : uifs) {
                if(uif.getUserName().equals(SaveUser.getLoginUserName())) {
                    continue;
                }
                UserListUI userListUI = new UserListUI();
                userListUI.setNameLabel(uif.getUserName());
                userListUI.setHeadImageView(uif.getUserPicture());
                contactsHashMap.put(uif.getUserName(), userListUI);
                contactMessageNumber.put(uif.getUserName(), 0);
                contactsList.getItems().add(userListUI);
            }
        });
    }

    /**
     * 保存除群聊以外的信息
     * */
    public void SaveMessage(Message message) {
        if(message.getTYPE() != MessageType.GROUPSMS) {
            String key = message.getName();
            if(key.equals(SaveUser.getLoginUserName())) {
                key = message.getTo();
            }
            if(messageLog.containsKey(key)) {
                messageLog.get(key).add(message);
            } else {
                LinkedList<Message> list = new LinkedList<>();
                list.add(message);
                messageLog.put(key, list);
            }
        }
    }

    /**
     * 处理界面切换时的消息显示
     * */
    public void switchChatUser() {
        chatBoxList.getItems().clear();
        int i = 0;
        LinkedList<Message> list = messageLog.get(ChatObject);
        if(list != null) {
            for(Message message : list) {
                if(message.getName().equals(SaveUser.getLoginUserName())) {
                    showMyMessage(message.getMessage());
                } else if(message.getTYPE() != MessageType.GROUPSMS) {
                    showOtherMessage(message);
                }
            }
        }
    }


    /**
     * 保存群聊信息
     * */
    public void saveGroupMessage(Message message) {
        if(message.getTYPE() == MessageType.GROUPSMS) {
            groupMessage.add(message);
        }
    }


    /**
     * 切换到群聊界面
     * */
    public void switchGroup() {
        chatBoxList.getItems().clear();
        LinkedList<Message> list = groupMessage;
        if(list != null) {
            for(Message message : groupMessage) {
                if(message.getName().equals(SaveUser.getLoginUserName())) {
                    showMyMessage(message.getMessage());
                } else {
                    showOtherMessage(message);
                }
            }
        }
    }

    /**
     * 用户上线提醒
     * */
    public void newUserNotification(Message message) {
        if(message.getName().equals(SaveUser.getLoginUserName()) || SaveSetting.isOnLinePrompt == false) {
            return;
        }
        Platform.runLater(()->{
            Image porofileImg = new Image(message.getHeadPicture(),50,50,false,false);
            TrayNotification tray = new TrayNotification();
            tray.setTitle("新用户上线");
            tray.setMessage("用户" + message.getName() + "加入了聊天");
            tray.setRectangleFill(Paint.valueOf("#2C3E50"));
            tray.setAnimationType(AnimationType.POPUP);
            tray.setImage(porofileImg);
            tray.showAndDismiss(Duration.seconds(5));
            sound("/resources/sounds/notification.wav");
        });
    }

    /**
     * 播放声音
     * */
    public void sound(String path) {
        try {
            String s = MainUIControl.class.getResource(path).toString();
            Media hit = new Media(s);
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开设置面板
     * */
    public void setting() {
        settingUIMain.showWindow(stageSetting);
    }


    /**
     * 发送改密命令
     * */
    public void chengePwds(Message message) {
        if(message != null) {
            try {
                Communication.send(message);
            } catch (Exception e) {
                new PopUpUI("提示：","更改失败!");
                e.printStackTrace();
            }
        }
    }

    /**
     * 打开历史消息面板
     * */
    public void chatHistory() {
        historyMain.showWindow(stageHistory);
    }

    /**
     * 发送查找历史消息的命令
     * */
    public void sendLookUpHistory(String name) {
        Message message = new Message(SaveUser.getLoginUserName(), "LookUpHistory", MessageType.HISTORY);
        message.setTo(name);
        try {
            Communication.send(message);
        } catch (Exception e) {
            new PopUpUI("提示：","查找失败!");
            e.printStackTrace();
        }
    }

    public void showHistoryMessage(Message message) {
        historyControl.show(message);
    }
}