/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapplication.general;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Xin
 */

@Named
@SessionScoped
public class UserNameBean implements Serializable{
    static final String c_sessionUserNameAttributeName = "sessionUserNameAttribute";
            
    private String userName;
    private final ArrayList<LoggedInUser> userList;
    
    public UserNameBean() {
        this.userName = null;
        this.userList = new ArrayList<>();
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    
    public ArrayList<LoggedInUser> getUserList() {
        userList.clear();
        
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        ServletContext application = session.getServletContext();
        
        HashMap<String, String> userMap = (HashMap<String, String>) application.getAttribute(SessionBindingListener.c_ApplicationLoggedInUserMapAttributeName);

        userMap.forEach((k, v)->{
            userList.add(new LoggedInUser(k,v));
        });
        
        return userList;
    }
    
    public void login() {
        try {
            // write username to current session attribute
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute(c_sessionUserNameAttributeName, new SessionBindingListener(this.userName));
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("result.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UserNameBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void logout() {
        try {
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.removeAttribute(c_sessionUserNameAttributeName);
            
            this.userName = null;
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UserNameBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isLoggedIn() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        SessionBindingListener listener = (SessionBindingListener) session.getAttribute(c_sessionUserNameAttributeName);
        
        return listener != null && this.userName != null;
    }
    
    public void viewLoggedInUsers() {
       try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("result.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UserNameBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
