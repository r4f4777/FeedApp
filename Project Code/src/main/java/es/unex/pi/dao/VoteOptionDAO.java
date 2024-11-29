package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;
import es.unex.pi.model.VoteOption;

public interface VoteOptionDAO {

    /**
     * Sets the database connection in this DAO.
     * 
     * @param conn Database connection.
     */
    void setConnection(Connection conn);

    /**
     * Gets a VoteOption by its ID.
     * 
     * @param id VoteOption ID.
     * @return VoteOption object with that ID.
     */
    VoteOption get(Integer id);

    /**
     * Gets all VoteOptions associated with a specific Poll.
     * 
     * @param pollId Poll ID.
     * @return List of VoteOptions for that Poll.
     */
    List<VoteOption> getAllByPollId(Integer pollId);

    /**
     * Adds a VoteOption to the database.
     * 
     * @param voteOption VoteOption object with details to add.
     * @return VoteOption ID or -1 if the operation failed.
     */
    Integer add(VoteOption voteOption);

    /**
     * Updates an existing VoteOption in the database.
     * 
     * @param voteOption VoteOption object with new details.
     * @return True if the operation succeeded, False otherwise.
     */
    boolean update(VoteOption voteOption);

    /**
     * Deletes a VoteOption from the database.
     * 
     * @param id VoteOption ID.
     * @return True if the operation succeeded, False otherwise.
     */
    boolean delete(Integer id);
}
