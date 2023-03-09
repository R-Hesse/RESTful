package org.ac.cst8277.hesse.ryan;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;

@RestController
public class UserController {
	
        RedirectView redirectView = new RedirectView();
        String emailGet;
        String subId;
        Boolean userIsPub;
        Boolean userIsSub;
        
        


		//VIEW ALL PUBLISHED MESSAGES FROM THE USER
        // SOCIAL APPLICATION TOKEN IS PULLED FOR AUTHORIZATION

	    
	    @GetMapping(value = { "/user/publishers" })
	    private List<User> getPublishers() { 
	    	List<User> publishers = new ArrayList<User>();
	        
	    	
	    	// PULLED SESSION TOKEN
	        String pulledToken = SocialApplication.getAuthToken();

	        User User = null;
	       try {

	    	   // SELECT ALL DATA WHERE ROLE ID = 333 & TOKEN = USER SESSION TOKEN
	    	   
	    	   Connection con = DBConnection.getConnectionToDatabase();
	           String select = "SELECT * FROM ums.users \n"
	           		+ "LEFT JOIN ums.users_has_roles ON users.id = users_has_roles.users_id \n"
	           		+ "LEFT JOIN ums.roles ON users_has_roles.roles_id = roles.id \n"
	           		+ "LEFT JOIN messages.messages ON users_has_roles.publisher_id = messages.publishers_id\n"
	           		+ "WHERE users_has_roles.roles_id = '333' AND token = " + pulledToken;
	           Statement statement = con.createStatement();
	           ResultSet rs = statement.executeQuery(select);
	           
	           while (rs.next()) {
	        	   
	        	   String id = rs.getString("id");
	               String name = rs.getString("name");
	               String email = rs.getString("email");
	               String password = rs.getString("password");
	               String createdOn = rs.getString("created");
	               String lastVisitId = rs.getString("last_visit_id");
	               String token = rs.getString("token");
	               String role = rs.getString("roles_id");
	               String pub = rs.getString("publisher_id");
	               String dog = rs.getString("dog_name");
	               String comment = rs.getString("comment");


	               // SET ALL DATA INTO AN OBJECT FOR THE AUTHORIZED USER TO VIEW 
	               
	               User = new User();
	               User.setUserId(id);
	               User.setName(name);
	               User.setEmail(email);
	               User.setPassword(password);
	               User.setCreatedOn(createdOn);
	               User.setLastVisit(lastVisitId);
	               User.setToken(token);
	               User.setRole(role);
	               User.setPub(pub);
	               User.setDog(dog);
	               User.setComment(comment);
	               
	               publishers.add(User);
	           }
	       } catch (SQLException exception) {
	           exception.printStackTrace();
	       }
	       return publishers;	
	    	
	    }
	   
	    //VIEW ALL MESSAGES FROM PUBLISHERS WHERE USER IS SUBSCRIBED
        // SOCIAL APPLICATION TOKEN IS PULLED FOR AUTHORIZATION

	   
	    @GetMapping(value = { "/user/subscriber" })
	    private List<User> getSubscribers() { 

	    	List<User> subscribers = new ArrayList<User>();
	        
	    	// PULLED SESSION TOKEN
	        String pulledToken = SocialApplication.getAuthToken();

	        
	        User User = null;
	       try {


	    	   // SELECT ALL DATA WHERE ROLE ID = 222 & TOKEN = USER SESSION TOKEN

	    	   Connection con = DBConnection.getConnectionToDatabase();
	           
	           
	           String select = "SELECT * FROM ums.users LEFT JOIN ums.users_has_roles ON users.id = users_has_roles.users_id WHERE users_has_roles.roles_id = '222' AND token = " + pulledToken;
	           Statement statement = con.createStatement();
	           ResultSet r = statement.executeQuery(select);
	           while (r.next()) {
	        	   
	        	   subId = r.getString("subscriber_id");
	        	   
	        	   
	               
	           }
	           
	           // SELECT ALL SUBSCRIPTION DATA USING THE USERS SUBSCRIPTION ID AND PULLED TOKEN
	           
	           String select2 = "SELECT * FROM ums.users \n"
	           		+ "LEFT JOIN ums.users_has_roles ON users.id = users_has_roles.users_id \n"
	           		+ "LEFT JOIN ums.roles ON users_has_roles.roles_id = roles.id \n"
	           		+ "LEFT JOIN messages.subscriptions ON users_has_roles.subscriber_id = subscriptions.subscribers_id\n"
	           		+ "LEFT JOIN messages.messages ON subscriptions.publishers_id = messages.publishers_id\n"
	           		+ "WHERE subscriptions.subscribers_id = '" + subId + "' AND token = " + pulledToken;
	           Statement statement2 = con.createStatement();
	           ResultSet rs = statement2.executeQuery(select2);
	           while (rs.next()) {
	        	   
	        	   String id = rs.getString("id");
	               String name = rs.getString("name");
	               String email = rs.getString("email");
	               String token = rs.getString("token");
	               String role = rs.getString("roles_id");
	               String sub = rs.getString("subscribers_id");
	               String pub = rs.getString("publishers_id");
	               String dog = rs.getString("dog_name");
	               String comment = rs.getString("comment");
	               
	               // SET ALL DATA INTO AN OBJECT FOR THE AUTHORIZED USER TO VIEW 


	               User = new User();
	               User.setUserId(id);
	               User.setName(name);
	               User.setEmail(email);
	               User.setToken(token);
	               User.setRole(role);
	               User.setSub(sub);
	               User.setPub(pub);
	               User.setDog(dog);
	               User.setComment(comment);
	               subscribers.add(User);
	           
	       }} catch (SQLException exception) {
	           exception.printStackTrace();
	       }
	       return subscribers;
	   	    }
	    
	    
	 // CREATE USER   
	@GetMapping(value = { "/user/create" })
    public ResponseEntity<Object> createNewUser(@RequestParam(value = "name")String name, @RequestParam(value = "email") String email, @RequestParam(value = "password")String password, @RequestParam(value = "pub") String pub, @RequestParam(value = "sub")String sub) throws IOException, SQLException {
        
    	// ADDS NEW USER TO DATABASE AFTER GIT AUTHENTICATES THE USER 
		
		// PULLED SESSION TOKEN
        String pulledToken = SocialApplication.getAuthToken();
    	
            Connection con = DBConnection.getConnectionToDatabase();
            
            String userUUID = UUID.randomUUID().toString();
            String tempAuthUUID = UUID.randomUUID().toString();
            String visitUUID = UUID.randomUUID().toString();
            LocalTime date = LocalTime.now();
            
            String time = date.toString();
            
            
            String autEmail = "'" + email + "'";
            
            
            // CHECKS NEW USER FOR ROLES
            
            if (pub.equals("Yes") & sub.equals("Yes")) {
            	
            	userIsPub = true;
            	userIsSub = true;
            	
            } else if (pub.equals("Yes")) {

            	userIsPub = true;
            	userIsSub = false;
            	
            }else if (sub.equals("Yes")) {
            	userIsPub = false;
            	userIsSub = true;
            } else {
            	userIsPub = false;
            	userIsSub = false;
            }
            

            // QUERIES TO ADD NEW USER
	        
            String insertQuery = "insert into ums.users values(?,?,?,?,?,?,?)";
            String insertQuery2 = "insert into ums.last_visit values(?,?,?)";
            String insertQuery3 = "INSERT INTO ums.users_has_roles (`id`, `users_id`, `roles_id`, `publisher_id`) SELECT  uuid(), `users`.`id`, 222, uuid() FROM ums.users WHERE users.token = " + pulledToken;
            String insertQuery4 = "INSERT INTO ums.users_has_roles (`id`, `users_id`, `roles_id`, `subscriber_id`) SELECT  uuid(), `users`.`id`, 333, uuid() FROM ums.users WHERE users.token = " + pulledToken;
            String selectUser = "SELECT * FROM ums.users WHERE email = " + autEmail;

            PreparedStatement statement = con.prepareStatement(insertQuery);
            PreparedStatement statement2 = con.prepareStatement(insertQuery2);
            PreparedStatement statement3 = con.prepareStatement(insertQuery3);
            PreparedStatement statement4 = con.prepareStatement(insertQuery4);


            
            Statement selectUserStatement = con.createStatement();
            ResultSet set1 = selectUserStatement.executeQuery(selectUser);
            while (set1.next()) {
         	    
         	    emailGet = set1.getString("email");
         	    
            }
            
            // CHECKS TO SEE IF THE EMAIL EXSISTS IN THE SYSTEM 
            if (email.equals(emailGet)) {

            	// ERROR IF THE EMAIL PROVIDED IS ALREADY REGISTERED WITH MY DOG'S LIFE

            	
            	System.setProperty("java.awt.headless", "false"); 

	            JOptionPane.showMessageDialog(null, "   User " + name + " has NOT been saved in the system. \n\n The EMAIL " + email + " is already registered. \n \n                            Please try again", "ERROR!!", 1, null);

                
            } 
            
            //RUNS IF THE EMAIL IS NOT PRESENT IN THE DATABASE AND ADDS THE USER PLUS A TEMPORARY TOKEN 
            else {
            	                
                
                statement.setString(1, userUUID);
                statement.setString(2, name);
                statement.setString(3, email);
                statement.setString(4, password);
                statement.setString(5, time);
                statement.setString(6, visitUUID);
                statement.setString(7, tempAuthUUID);

                
                statement2.setString(1, visitUUID);
                statement2.setString(2, time);
                statement2.setString(3, time);
                
                // ASSIGNS DETAILS TO USER
                statement2.execute();

                statement.execute();
                
                // ASSIGNS ROLES TO USER 
                
                if (userIsPub == true & userIsSub == true) {
                	
                	statement3.execute();
                	statement4.execute();
             	
                } else if (userIsPub) {

                	statement3.execute();
                	
                }else if (userIsSub) {
                	         	
                	statement4.execute();
                	
                } else {
                	
                	System.out.println("USER REMAINS UNAUTHORIZED");
                }


                // ADD THE QUERIES TO THE ROLES & MESSAGES TABLES WHERE NEEDED 
                // WORKING HERE
                
                // INFORMS THE USER THAT THEY HAVE BEEN INTRODUCED TO THE SYSTEM
                
                System.setProperty("java.awt.headless", "false"); 

                JOptionPane.showMessageDialog(null, "User " + name + " has been saved in the system", "Hello There", 1, null);
                
                
                // RETURNS THE USER TO THE LOGIN PAGE
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("http://localhost:8080"))
                        .build();
         	} 
   	
		return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:8080"))
                .build();
    }
	

	
	// LOGIN USER
    @GetMapping(path = { "/user/login" })
    public String loginUser(@RequestParam(value = "userId")String userId, @RequestParam(value = "email")String email, @RequestParam(value = "password")String password ) throws IOException {
    	    
    	String token = null;
    	String login = null;
    	
    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            String select = "SELECT * FROM ums.users WHERE id = " + userId;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
         	    
         	    String ema = rs.getString("email");
                String pwd = rs.getString("password");

               if (email != ema | password != pwd) {
                	   token = "AUTHORIZED";
                   } else {
                	   token = "DENIED";

                   }
                   
                   login = "User login has been " + token + " for User: " + userId;
           		   token = null;
                   return login;
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return login;
    }
    
	// LOGOUT USER
    @GetMapping(path = { "/user/logout" })
    public String logoutUser(@RequestParam(value = "userId")String userId) throws IOException {
    	    
    	String logout = null;
    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            String select = "SELECT * FROM ums.users WHERE id = " + userId;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
       
            	   logout = "User logout has been AUTHORIZED for User: " + userId;
                   return logout;
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return logout;
    }
	
	
	// SEARCH USER
    @GetMapping(path = { "/user/search" })
    public List<User> searchUser(@RequestParam(value = "userId")String userId) throws IOException {
    	
    	List<User> users = new ArrayList<User>();
    
    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            String select = "SELECT * FROM ums.users WHERE id =" + userId;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
         	    
         	    String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String created = rs.getString("created");
                String lastVisit = rs.getString("last_visit_id");

                
                   User user= new User();
	               user.setUserId(id);
	               user.setName(name);
	               user.setEmail(email);
	               user.setPassword(password);
	               user.setCreatedOn(created);
	               user.setLastVisit(lastVisit);
	               users.add(user);
            
        		return users;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return users;
    }
    
    //UPDATE USER
    @GetMapping(value = { "/user/update" })
    public User updateMessage(@RequestParam(value = "userId")String userId, @RequestParam(value = "name")String name, @RequestParam(value = "email") String email, @RequestParam(value = "password") String password) throws IOException {
        
    	User user = null;
    	
    	try {
            Connection con = DBConnection.getConnectionToDatabase();
            // UPDATE STATEMENT
            String updateQuery = "UPDATE ums.users SET name = " + name + ", email = " + email + ", password = " + password + " WHERE id = " + userId;
            PreparedStatement statement = con.prepareStatement(updateQuery);
            // UPDATE USER ON DATABASE
            
            user = new User();
            user.setUserId("6");
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setCreatedOn("1605194745");
            user.setLastVisit("6");
            
            
            statement.setString(1, "6");
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, "1605194745");
            statement.setString(6, "6");

            statement.execute();
            
            return user;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    	return user;
    }
    
   // DELETE USER
    @GetMapping(value = { "/user/delete" })
 	public String deleteMessage(@RequestParam(value = "userId")String userId) {
    	 	
     	try {
             Connection con = DBConnection.getConnectionToDatabase();
             // DELETE STATEMENT
             String deleteQuery = "DELETE FROM ums.users WHERE id = " + userId;
             PreparedStatement statement = con.prepareStatement(deleteQuery);

             statement.execute();
             
             
         } catch (SQLException e) {
             e.printStackTrace();
         }
     	String unsub = "User Number: " + userId + " Deleted";
     	return unsub;
    }
}
