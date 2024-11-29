package es.unex.pi.model;


import java.util.Map;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;

public class User {

	private Integer id;
	private String username;
	private String email;
	private String password;
    
    public User() {
       
    }
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean validate(Map<String, String> messages){
	   if(password.trim().isEmpty()||password==null) {
	      messages.put("error", "Empty password");
		     } else if(!password.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) {
		           messages.put("error", "Invalid password: " + password.trim());
		     }
	   if(messages.isEmpty()) return true; 
		   else return false;
	}
	
}
