package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import es.unex.pi.model.User;

public class JDBCUserDAOImpl implements UserDAO {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());

    @Override
    public User getByUsername(String username) {
        if (conn == null) return null;

        User user = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
            if (rs.next()) {
                user = new User();
                fromRsToUser(rs, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        if (conn == null) return null;

        User user = null;

        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);  
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                fromRsToUser(rs, user);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (user != null) {
            System.out.println(user.getId() + "");
        } else {
            System.out.println("No user found with email: " + email);
        }
        
        return user;
    }


    @Override
    public List<User> getAll() {
        if (conn == null) return null;

        List<User> users = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User user = new User();
                fromRsToUser(rs, user);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    
    @Override
	public Integer add(User user) {
		Integer id=-1;
		long lastidu=-1;
		if (conn != null){

			Statement stmt;
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='users'");			 
				if (!rs.next()) return -1; 
				lastidu=rs.getInt("seq");
								
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Users (email,password,username) VALUES('"
									+user.getEmail()+"','"
									+user.getPassword()+"','"
									+user.getUsername()+"')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='users'");			 
				if (!rs.next()) return -1; 
				id=rs.getInt("seq");
				if (id<=lastidu) return -1;
											
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return id;
	}
    
    
    


    @Override
    public boolean update(User user) {
        boolean success = false;
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(
                    "UPDATE users SET email = '" + user.getEmail()
                    + "' WHERE username = '" + user.getUsername() + "'"
                );
                logger.info("Updating User: " + user.getUsername());
                success = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    @Override
    public boolean delete(String username) {
        boolean success = false;
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM users WHERE username = '" + username + "'");
                logger.info("Deleting User by username: " + username);
                success = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
    
    

    private void fromRsToUser(ResultSet rs, User user) throws SQLException {
    	user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));  // Verifica que esta línea esté presente
    }

    @Override
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
