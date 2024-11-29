package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Poll;


public interface PollDAO {

    /**
     * Sets the database connection in this DAO.
     *
     * @param conn Database connection.
     */
    void setConnection(Connection conn);

    /**
     * Gets a poll from the DB using the question.
     *
     * @param question Question of the poll.
     * @return Poll object with that question.
     */
    Poll get(String question);
    
    /**
     * Gets a poll from the DB using the question.
     *
     * @param question id of the poll.
     * @return Poll object with that id.
     */
    Poll getById(Integer id);

    /**
     * Gets all polls from the database.
     *
     * @return List of all polls.
     */
    List<Poll> getAll();

    /**
     * Adds a poll to the database.
     *
     * @param poll Poll object with poll details.
     * @return Poll identifier or -1 if the operation failed.
     */
    Integer add(Poll poll);

    /**
     * Updates an existing poll in the database.
     *
     * @param poll Poll object with new details of the existing poll.
     * @return True if the operation was successful, False otherwise.
     */
    boolean update(Poll poll);

    /**
     * Deletes a poll from the database.
     *
     * @param question Question of the poll to delete.
     * @return True if the operation was successful, False otherwise.
     */
    boolean delete(Integer id);
}