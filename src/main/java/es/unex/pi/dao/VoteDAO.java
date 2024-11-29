package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.User;
import es.unex.pi.model.Vote;

public interface VoteDAO {

    /**
     * Sets the database connection in this DAO.
     * 
     * @param conn Database connection.
     */
    void setConnection(Connection conn);

    /**
     * Gets a Vote by its ID.
     * 
     * @param id Vote ID.
     * @return Vote object with that ID.
     */
    Vote get(String id);

    /**
     * Gets all Votes by a specific User ID.
     * 
     * @param userId User ID.
     * @return List of Votes associated with the User.
     */
    List<Vote> getAllByUserId(Integer userId);
   
    /**
     * Gets all Users from the database.
     * 
     * @return List of all Votes.
     */
    List<Vote> getAll();
    
    
    //Vote getByUserAndVoteOptionId(int userId, int voteOptionId);
    Vote getByUserAndPollId(int userId, int pollId);
    /**
     * Adds a Vote to the database.
     * 
     * @param vote Vote object to add.
     * @return Vote ID or -1 if the operation failed.
     */
    long add(Vote vote);

    /**
     * Updates an existing Vote in the database.
     * 
     * @param vote Vote object with updated details.
     * @return True if the operation succeeded, False otherwise.
     */
    boolean update(Vote vote);

    /**
     * Deletes a Vote from the database by ID.
     * 
     * @param id Vote ID.
     * @return True if the operation succeeded, False otherwise.
     */
    boolean delete(String id);
}
