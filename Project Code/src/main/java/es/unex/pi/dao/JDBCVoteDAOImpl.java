package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import es.unex.pi.model.Vote;
import es.unex.pi.model.User;
import es.unex.pi.model.VoteOption;

public class JDBCVoteDAOImpl implements VoteDAO {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(JDBCVoteDAOImpl.class.getName());

    @Override
    public Vote get(String id) {
        if (conn == null) return null;

        Vote vote = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vote WHERE id = '" + id + "'");
            if (rs.next()) {
                vote = new Vote();
                fromRsToVote(rs, vote);
                logger.info("Fetching Vote by ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vote;
    }
    
    @Override
    public List<Vote> getAll() {
        if (conn == null) return null; 

        List<Vote> votes = new ArrayList<>(); 

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vote"); 

            while (rs.next()) {
                Vote vote = new Vote();
                fromRsToVote(rs, vote); 
                votes.add(vote);
                logger.info("Fetching Vote ID: " + vote.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return votes; 
    }



    @Override
    public List<Vote> getAllByUserId(Integer userId) {
        if (conn == null) return null;

        List<Vote> votes = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vote WHERE user_id = '" + userId + "'");
            while (rs.next()) {
                Vote vote = new Vote();
                fromRsToVote(rs, vote);
                votes.add(vote);
                logger.info("Fetching Vote for User ID: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

   
    
    @Override
    public long add(Vote vote) {
        long id = -1;
        if (conn != null) {
            String sql = "INSERT INTO Vote (publishedAt, user_id, vote_option_id) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setTimestamp(1, Timestamp.from(vote.getPublishedAt()));

                // Verificar si el usuario está asociado y utilizar su ID
                if (vote.getUser() != null) {
                    stmt.setInt(2, vote.getUser().getId());
                } else {
                    stmt.setNull(2, java.sql.Types.INTEGER);
                }

                // Agregar el ID de la opción de voto
                stmt.setInt(3, vote.getVoteOptionId());

                // Ejecutar la inserción y obtener la clave generada
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getLong(1);  // Obtener el ID generado automáticamente
                        }
                    }
                }
                logger.info("Creating Vote for User ID: " + (vote.getUser() != null ? vote.getUser().getId() : "null") +
                            " and Vote Option ID: " + vote.getVoteOptionId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

   
    @Override
    public Vote getByUserAndPollId(int userId, int pollId) {
        if (conn == null) return null;

        Vote vote = null;
        String sql = "SELECT v.* FROM Vote v " +
                     "JOIN VoteOption vo ON v.vote_option_id = vo.id " +
                     "WHERE v.user_id = ? AND vo.poll_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, pollId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vote = new Vote();
                    fromRsToVote(rs, vote); // Llenar el objeto `Vote` con los datos obtenidos
                    logger.info("Fetching Vote for User ID: " + userId + " and Poll ID: " + pollId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vote;
    }



    @Override
    public boolean update(Vote vote) {
        boolean success = false;
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(
                    "UPDATE Vote SET publishedAt = '" + Timestamp.from(vote.getPublishedAt())
                    + "', user_id = '" + vote.getUser().getId()
                    + "', vote_option_id = '" + vote.getVoteOptionId()
                    + "' WHERE id = '" + vote.getId() + "'"
                );
                logger.info("Updating Vote: " + vote.getId());
                success = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    @Override
    public boolean delete(String id) {
        boolean success = false;
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM Vote WHERE id = '" + id + "'");
                logger.info("Deleting Vote by ID: " + id);
                success = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    private void fromRsToVote(ResultSet rs, Vote vote) throws SQLException {
        vote.setId(rs.getInt("id"));
        
        // Obtener la marca de tiempo como long y convertirla a Instant
        long publishedAtMillis = rs.getLong("publishedAt");
        vote.setPublishedAt(Instant.ofEpochMilli(publishedAtMillis));

        // Asignar usuario y opción de voto
        User user = new User();
        user.setId(rs.getInt("user_id"));
        vote.setUser(user);

        VoteOption voteOption = new VoteOption();
        voteOption.setId(rs.getInt("vote_option_id"));
        vote.setVoteOptionId(voteOption.getId());
    }

    @Override
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

}
