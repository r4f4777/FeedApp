package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;
import es.unex.pi.model.User;

public interface UserDAO {

    /**
     * Sets the database connection in this DAO.
     * 
     * @param conn Database connection.
     */
    void setConnection(Connection conn);

    /**
     * Gets a User by username.
     * 
     * @param username Username of the User.
     * @return User object with that username.
     */
    User getByUsername(String username);

    /**
     * Gets a User by email.
     * 
     * @param email Email of the User.
     * @return User object with that email.
     */
    User getByEmail(String email);

    /**
     * Gets all Users from the database.
     * 
     * @return List of all Users.
     */
    List<User> getAll();

    /**
     * Adds a User to the database.
     * 
     * @param user User object to add.
     * @return True if the operation succeeded, False otherwise.
     */
    Integer add(User user);

    /**
     * Updates an existing User in the database.
     * 
     * @param user User object with updated details.
     * @return True if the operation succeeded, False otherwise.
     */
    boolean update(User user);

    /**
     * Deletes a User from the database by username.
     * 
     * @param username Username of the User to delete.
     * @return True if the operation succeeded, False otherwise.
     */
    boolean delete(String username);
}
