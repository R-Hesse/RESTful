package org.ac.cst8277.hesse.ryan;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



// PUBLISHER CONTROLLER 

@RestController
public class PublisherController {
	
	// PUBLISHER LIST 
	
	private List<Publisher> publishers = createList();
	

	
    private List<Publisher> createList() { 
    	
        publishers = new ArrayList<Publisher>();
        
        Publisher Publisher = null;
       try {
           // GET CONNECTION AND ALL THE DATA
           Connection con = DBConnection.getConnectionToDatabase();
           String select = "select * from messages.publishers";
           Statement statement = con.createStatement();
           ResultSet rs = statement.executeQuery(select);
           while (rs.next()) {
        	   
        	   String id = rs.getString("id");
               String dog = rs.getString("dog_name");
               String comment = rs.getString("comment");
           
               
               Publisher = new Publisher();
               Publisher.setPublisherId(id);
               Publisher.setDogName(dog);
               Publisher.setComment(comment);
               publishers.add(Publisher);
           }
       } catch (SQLException exception) {
           exception.printStackTrace();
       }
       return publishers;
   }
	
    @GetMapping(value = { "/publish/message" })
    public Message publishMessage(@RequestParam(value = "dogName")String dogName, @RequestParam(value = "content") String content) throws IOException {
        
    	Message message = null;
    	
    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            // INSERT STATEMENT
            String insertQuery = "insert into messages.messages values(?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(insertQuery);
            // ADD MESSAGE TO DATABASE
            
            message = new Message();
            message.setMessageId("77");
            message.setDog(dogName);
            message.setContent(content);
            message.setCreatedOn("1605194745");
            message.setPubId("1");
            
            
            statement.setString(1, "77");
            statement.setString(2, dogName);
            statement.setString(3, content);
            statement.setString(4, "1605194745");
            statement.setString(5, "1");

            statement.execute();

            return message;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }
    
    //UPDATE MESSAGE
    @GetMapping(value = { "/publish/update" })
    public Message updateMessage(@RequestParam(value = "msgId")String msgId, @RequestParam(value = "dogName")String dogName, @RequestParam(value = "content") String content) throws IOException {
        
    	Message message = null;
    	
    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            // UPDATE STATEMENT
            String updateQuery = "UPDATE messages.messages SET dog_name = " + dogName + ", content = " + content + " WHERE id = " + msgId;
            PreparedStatement statement = con.prepareStatement(updateQuery);
            // UPDATE MESSAGE ON DATABASE
            
            message = new Message();
            message.setMessageId("77");
            message.setDog(dogName);
            message.setContent(content);
            message.setCreatedOn("1605194745");
            message.setPubId("1");
            
            
            statement.setString(1, "77");
            statement.setString(2, dogName);
            statement.setString(3, content);
            statement.setString(4, "1605194745");
            statement.setString(5, "1");

            statement.execute();
            
            return message;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    	return message;
    }
    
   // DELETE MESSAGE
    @GetMapping(value = { "/publish/delete" })
 	public String deleteMessage(@RequestParam(value = "msgId")String msgId) {
    	 	
     	try {
             Connection con = DBConnection.getConnectionToDatabase();
             // DELETE STATEMENT
             String deleteQuery = "DELETE FROM messages.messages WHERE id = " + msgId;
             PreparedStatement statement = con.prepareStatement(deleteQuery);

             statement.execute();
             
             
         } catch (SQLException e) {
             e.printStackTrace();
         }
     	String unsub = "Message Number: " + msgId + " Deleted";
     	return unsub;
    }
    
    // GET SUBSCRIBERS
    @GetMapping(value = { "/publish/subscribers" })
    private List<Subscriber> getSubscribers(@RequestParam(value = "pub_id")String pub_id) { 
    	
        List<Subscriber> subscribers = new ArrayList<Subscriber>();
        
        Subscriber Subscriber = null;
        
       try {
           // GET CONNECTION AND ALL THE DATA
           Connection con = DBConnection.getConnectionToDatabase();
           String select = "SELECT * FROM messages.subscribers LEFT JOIN messages.subscriptions ON subscribers.id = subscriptions.subscribers_id WHERE subscriptions.publishers_id = " + pub_id;
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

    

        
}