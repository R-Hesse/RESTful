package org.ac.cst8277.hesse.ryan;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//SUBSCRIBER CONTROLLER 

@RestController
public class SubscriberController {
	
	// SUBSCRIBER LIST
	
	private List<Subscriber> subscribers = createList();
	
	private List<Subscriber> createList() { 
    	
        subscribers = new ArrayList<Subscriber>();
        
        Subscriber Subscriber = null;
       try {
           // GET CONNECTION AND ALL THE DATA
           Connection con = DBConnection.getConnectionToDatabase();
           String select = "select * from messages.subscribers";
           Statement statement = con.createStatement();
           ResultSet rs = statement.executeQuery(select);
           while (rs.next()) {
        	   
        	   String id = rs.getString("id");
               String comment = rs.getString("comment");
               
               
               Subscriber = new Subscriber();
               Subscriber.setSubscriberId(id);
               Subscriber.setComment(comment);
               subscribers.add(Subscriber);
           }
       } catch (SQLException exception) {
           exception.printStackTrace();
       }
       return subscribers;
   }

	// SEARCH MESSAGE
    @GetMapping(path = { "/subscriber/message" })
    public List<Message> searchMessage(@RequestParam(value = "msgId")String msgId) throws IOException {
    	
    	List<Message> messages = new ArrayList<Message>();
    
    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            String select = "SELECT * FROM messages.messages WHERE id =" + msgId;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
         	   
         	   String id = rs.getString("id");
                String dogName = rs.getString("dog_name");
                String comment = rs.getString("comment");
                String createdOn = rs.getString("created");
                String publisher_Id = rs.getString("publishers_id");

                
                Message message = new Message();
                message.setMessageId(id);
                message.setDog(dogName);
                message.setContent(comment);
                message.setCreatedOn(createdOn);
                message.setPubId(publisher_Id);
                
                messages.add(message);
                
                System.out.println(message);
                
                System.out.println(select);


        		return messages;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return messages;
    }
    
 // SEARCH PUBLISHERS
    @GetMapping(path = { "/subscriber/publisher" })
    public List<Publisher> searchPublisher(@RequestParam(value = "pubId")String pubId) throws IOException {
    	
    	List<Publisher> publishers = new ArrayList<Publisher>();
    
    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            String select = "SELECT * FROM messages.messages WHERE publishers_id =" + pubId;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
         	   
         	   String id = rs.getString("id");
                String dogName = rs.getString("dog_name");
                String comment = rs.getString("comment");
              

                
                Publisher publisher = new Publisher();
                publisher.setPublisherId(id);
                publisher.setDogName(dogName);
                publisher.setComment(comment);
                
                
                publishers.add(publisher);
                
               
        		return publishers;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return publishers;
    }
    
       
}