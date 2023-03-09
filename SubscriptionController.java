package org.ac.cst8277.hesse.ryan;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// SUBSCRIPTION CONTROLLER

@RestController
public class SubscriptionController {
	
	// SUBSCRIPTION LIST
	private List<Subscription> subscriptions = createList();
	private List<Subscription> subs;

	
	private List<Subscription> createList() { 
    	
        subscriptions = new ArrayList<Subscription>(); // USER LIST FOR DB
        
        Subscription Subscription = null;
       try {
           // GET CONNECTION AND ALL THE DATA
           Connection con = DBConnection.getConnectionToDatabase();
           String select = "select * from messages.subscriptions";
           Statement statement = con.createStatement();
           ResultSet rs = statement.executeQuery(select);
           while (rs.next()) {
        	   
        	   String pubId = rs.getString("publishers_id");
               String subId = rs.getString("subscribers_id");
               
               
               Subscription = new Subscription();
               Subscription.setPublisherId(pubId);
               Subscription.setSubscriberId(subId);
               subscriptions.add(Subscription);
           }
       } catch (SQLException exception) {
           exception.printStackTrace();
       }
       return subscriptions; // WILL DISPLAY DATABASE
   }
   
	
	
	public List<Subscription> firstPage() throws IOException {
		return subscriptions;
	}
	
   
 
	// SUBSCRIBE    
    @GetMapping(value = "/subscribe")
    public String subscribe(@RequestParam(value = "pubId")String pubId, @RequestParam(value = "subId") String subId) throws IOException {

        Subscription subscript = new Subscription();

    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            // INSERT STATEMENT
            String insertQuery = "insert into messages.subscriptions values(?,?)";
            PreparedStatement statement = con.prepareStatement(insertQuery);
            // ADD SUB TO DATABASE
            statement.setString(1, pubId);
            statement.setString(2, subId);
            
            subscript.setPublisherId(pubId);
            subscript.setSubscriberId(subId);
            statement.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	String sub = "Subscriber: " + subId + " Subscribed to Publisher: " + pubId;
     	return sub;   
     	}

    // UNSUBSCRIBE
    @GetMapping(value = { "/unsubscribe" })
 	public String unsubscribe(@RequestParam(value = "pubId")String pubId, @RequestParam(value = "subId") String subId) {
    	
    	String delete = " publishers_id = " + pubId + " AND subscribers_id = " + subId;
 	
     	try {
             Connection con = DBConnection.getConnectionToDatabase();
             // DELETE STATEMENT
             String deleteQuery = "DELETE FROM messages.subscriptions WHERE" + delete;
             PreparedStatement statement = con.prepareStatement(deleteQuery);

             statement.execute();
             
         } catch (SQLException e) {
             e.printStackTrace();
         }
     	String unsub = "Subscriber: " + subId + " Unubscribed From Publisher: " + pubId;
     	return unsub;
    }
    

    @GetMapping(value = { "/subscribe/view" })
    public List<Subscription> viewSub(@RequestParam(value = "email")String email) throws IOException {
        
    	subs = new ArrayList<Subscription>();
        
        Subscription subscription = null;
        
        String autEmail = "'" + email + "'";

    	
       try {
    	   Connection con = DBConnection.getConnectionToDatabase();
           // INSERT STATEMENT
           String selectQuery = "SELECT users.name, subscriptions.publishers_id, subscriptions.subscribers_id FROM ums.users LEFT JOIN ums.users_has_roles ON users.id = users_has_roles.users_Id LEFT JOIN messages.subscriptions ON users_has_roles.publisher_id = subscriptions.publishers_id WHERE users.email = " + autEmail;
           PreparedStatement statement = con.prepareStatement(selectQuery);
           ResultSet set = statement.executeQuery(selectQuery);

           
           while (set.next()) {
        	   
        	   String name = set.getString("users.name");
        	   String pubId = set.getString("subscriptions.publishers_id");
        	   String subId = set.getString("subscriptions.subscribers_id");
        	   
        	
           
               subscription = new Subscription();
               subscription.setName(name);
               subscription.setPublisherId(pubId);
               subscription.setSubscriberId(subId);
               
               subs.add(subscription);
              
           }
       } catch (SQLException exception) {
           exception.printStackTrace();
       }
       return subs;
    }
        
    
    
    
}
        
