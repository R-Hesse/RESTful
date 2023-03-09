package org.ac.cst8277.hesse.ryan;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.http.HttpStatus;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SuppressWarnings("deprecation")
@SpringBootApplication
@RestController

public class SocialApplication extends WebSecurityConfigurerAdapter {
	
	static String tokenUUID;

	
	    public static void main(String[] args) {
	        SpringApplication.run(SocialApplication.class, args);
	    }
	    
	    
	    @GetMapping("/user")
	    public User user(@AuthenticationPrincipal OAuth2User principal) throws IOException {
	 		 
	    	User user = new User();
	    	User user2 = new User();
        	List<String> roles = new ArrayList<String>();
        	int role = 0;


        	// GET GIT USER DETAILS
        	Object att = principal.getAttribute("id");
        	String gitId = att.toString();
        	
	    	
        	// USER OBJECT WITH GIT DETAILS
	    	user.setName(principal.getAttribute("name"));
	    	user.setEmail(principal.getAttribute("email"));
	    	
	    	
	    	// CUSTOM UUID TOKEN WITH GIT ID 
	    	// FORMATTING FOR QUERY DETAILS
	        String customUUID = UUID.randomUUID() + "-" + gitId;
	        tokenUUID = "'" + customUUID.toString() + "'";
	        String autName = "'" + user.getName() + "'";
	        String autEmail = "'" + user.getEmail() + "'";
	        
	        
	        
	        

	        
	        try {
	            Connection con = DBConnection.getConnectionToDatabase();
	            
	            //ADD TOKEN TO USER INSTANCE
	            String updateQuery = "UPDATE ums.users SET token = " + tokenUUID + " WHERE name = " + autName + " AND email = " + autEmail;

	            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
	            updateStatement.execute();
	            
		        int updateCount = updateStatement.executeUpdate();
		        
		        // ONLY RUNS IF USER TOKEN GETS UPDATED
		        if (updateCount > 0) {
		        	
		        	String selectUser = "SELECT * FROM ums.users WHERE name = " + autName + " AND email = " + autEmail;
		            Statement selectUserStatement = con.createStatement();
		            ResultSet set1 = selectUserStatement.executeQuery(selectUser);
		            while (set1.next()) {
		            	
		            	// CHECKS DATABASE FOR EXSISTING USER
		         	    
		            	String id = set1.getString("id");
		            	String name = set1.getString("name");
		         	    String email = set1.getString("email");
		                String pwd = set1.getString("password");
		                String createdOn = set1.getString("created");
		                String lastVisit = set1.getString("last_visit_id");
		                String token = set1.getString("token");

		                // GATHERS EXSISTING USER DATA 
		                
		                user2.setUserId(id);
		                user2.setName(name);
		                user2.setEmail(email);
		                user2.setPassword(pwd);
		                user2.setCreatedOn(createdOn);
		                user2.setLastVisit(lastVisit);
		                user2.setToken(token);
		                
		                String userID = "'" + user2.getUserId() + "'";
		            
		                // CHECKS ROLE ACCESS AND AUTHORIZATION
		                
		            String selectRole = "SELECT * FROM ums.users_has_roles WHERE users_id = " + userID;
			        Statement selectRoleStatement = con.createStatement();
			        selectRoleStatement.executeQuery(selectRole);
			        
			        try {
			        ResultSet set2 = selectRoleStatement.executeQuery(selectRole);
			        
			        while (set2.next()) {
			        	
			        	
			        	roles.add(set2.getString(2));
			        	
				      	String auth = set2.getString("roles_id");
				      	
				      	role = Integer.parseInt(auth);
		            	
				      	// IF USER IS ADMIN
		            	if (role == 111) {
		            		
			            	System.out.println("User " + user2.getName() + " is an Authorized Admin");
			            	
			            	// ADMIN FEATURES GO HERE

		            	} 
				      		// IF USER IS SUBSCRIBER
		            		else if (role == 222) {
		            		
			            	System.out.println("User " + user2.getName() + " is an Authorized Subscrier");
			            	
			            	// SUBSCRIBER FEATURES GO HERE



		            	} 
		            		// IF USER IS PUBLISHER
		            		else if (role == 333) {
		            		
			            	System.out.println("User " + user2.getName() + " is an Authorized Publisher");
			            	
			            	// PUBLISHER FEATURES GO HERE


		            		
		            	} 
		            		//IF USER IS NOT AUTHORIZED TO UTLIZE THE SYSTEM
		            		else {
			            	System.out.println("User " + user2.getName() + " is NOT Authorized on the platform");
		            	}
			        } }
		             finally {
		            	 selectRoleStatement.close();
		            }

		            
		            return user2;
		            
		        } 
		        	
		       } 
		        // RUNS IF USER TOKEN DOESNT GET UPDATED DUE TO LACK OF DETAILS IN THE SYSTEM
		        else {
	            
	            	System.out.println("USER IS NOT AUTHORIZED TO ACCESS THE SYSTEM");

	            	
	            	System.setProperty("java.awt.headless", "false"); 

	                JOptionPane.showMessageDialog(null, "User has not been authorized to access My Dog's Life. \n \n Please CREATE a user in the system.", "404 ERROR!!", role, null);
	            	
	            
	            } }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }

	    	return user2;
	    }
	    
	    
	    // ERROR MAPPING
	    
	    @GetMapping("/error")
	    public String error(HttpServletRequest request) {
	    	String message = (String) request.getSession().getAttribute("error.message");
	    	request.getSession().removeAttribute("error.message");
	    	return message;
	    }
	    
	    public static String getAuthToken() {
				    	
	    	try {
				return tokenUUID;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tokenUUID;
	    	
	    }
	    
	        

	    // MODIFIES THE VIEW
    public void configure(HttpSecurity http) throws Exception {
    	// @formatter:off
        http
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login();
        // @formatter:on
        
     // @formatter:off
        http
            // ... existing code here
            .logout(l -> l
                .logoutSuccessUrl("/").permitAll()
            );
            // ... existing code here
        // @formatter:on
        
     // @formatter:off
        http
            // ... existing code here
            .csrf(c -> c
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            );
            // ... existing code here
        // @formatter:on
        
     // @formatter:off
    	http
    	    // ... existing configuration
    	    .oauth2Login(o -> o
                .failureHandler((request, response, exception) -> {
    			    request.getSession().setAttribute("error.message", exception.getMessage());
                })
            );
           
    }
   
    // CUSTOM ERROR CODE METHOD
    public class Error
    {

        public static void infoBox(String infoMessage, String titleBar)
        {
            JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

}
