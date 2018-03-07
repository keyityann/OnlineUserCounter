/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapplication.general;

import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

@WebListener()
/**
 *
 * @author Xin
 */
public class SessionBindingListener implements HttpSessionBindingListener  {

    public static final String c_ApplicationLoggedInUserMapAttributeName = "loggedinUserMap";
    private String userName = null;
    
    SessionBindingListener(String userName) {
        this.userName = userName;
    }
    
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        // add new {sessionId: username} pair to hashmap
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        
        HashMap<String, String> loggedinUserMap = (HashMap<String, String>)application.getAttribute(c_ApplicationLoggedInUserMapAttributeName);
        if (loggedinUserMap == null)
        {
            loggedinUserMap = new HashMap<>();
            application.setAttribute(c_ApplicationLoggedInUserMapAttributeName, loggedinUserMap);
        }
        loggedinUserMap.put(session.getId(), this.userName);
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        
        HashMap<String, String> loggedinUserMap = (HashMap<String, String>)application.getAttribute(c_ApplicationLoggedInUserMapAttributeName);
        if (loggedinUserMap != null)
        {
            loggedinUserMap.remove(session.getId(), this.userName);
        }
    }
}
