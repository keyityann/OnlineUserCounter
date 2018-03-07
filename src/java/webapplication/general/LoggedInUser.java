/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapplication.general;

/**
 *
 * @author Xin
 */
public class LoggedInUser {
    private String sessionId = null;
    private String userName = null;

    public LoggedInUser(String sessionId, String userName) {
        this.sessionId = sessionId;
        this.userName = userName;
    }
        
    public String getSessionId() {
        return sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
