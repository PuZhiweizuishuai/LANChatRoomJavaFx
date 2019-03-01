package ChatMessage.user;

public class UserInformation {
    private String userName;
    private String userEmail;
    private String userPassword;

    public UserInformation(String userName, String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
