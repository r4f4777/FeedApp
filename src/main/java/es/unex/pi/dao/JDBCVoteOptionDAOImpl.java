package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.VoteOption;

public class JDBCVoteOptionDAOImpl implements VoteOptionDAO {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(JDBCVoteOptionDAOImpl.class.getName());

    @Override
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    @Override
    public VoteOption get(Integer id) {
        if (conn == null) return null;

        VoteOption voteOption = null;
        String query = "SELECT * FROM VoteOption WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                voteOption = new VoteOption();
                fromRsToVoteOption(rs, voteOption);
                logger.info("Fetching VoteOption by ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voteOption;
    }

    @Override
    public List<VoteOption> getAllByPollId(Integer pollId) {
        if (conn == null) return null;

        List<VoteOption> voteOptions = new ArrayList<>();
        String query = "SELECT * FROM VoteOption WHERE poll_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pollId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VoteOption voteOption = new VoteOption();
                fromRsToVoteOption(rs, voteOption);
                voteOptions.add(voteOption);
                logger.info("Fetching VoteOption for Poll ID: " + pollId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voteOptions;
    }

    @Override
    public Integer add(VoteOption voteOption) {
        Integer id = null;
        if (conn != null) {
            String query = "INSERT INTO VoteOption (caption, presentationOrder, poll_id) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, voteOption.getCaption());
                stmt.setInt(2, voteOption.getPresentationOrder());
                stmt.setInt(3, voteOption.getPollId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating vote option failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                        voteOption.setId(id);
                    } else {
                        throw new SQLException("Creating vote option failed, no ID obtained.");
                    }
                }
                logger.info("Creating VoteOption: " + voteOption.getCaption());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public boolean update(VoteOption voteOption) {
        if (voteOption.getCaption() == null || voteOption.getCaption().isEmpty()) {
            throw new IllegalArgumentException("Caption cannot be null or empty");
        }

        boolean success = false;
        if (conn != null) {
            String query = "UPDATE VoteOption SET caption = ?, presentationOrder = ?, poll_id = ? WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, voteOption.getCaption());
                stmt.setInt(2, voteOption.getPresentationOrder());
                stmt.setInt(3, voteOption.getPollId());
                stmt.setInt(4, voteOption.getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    success = true;
                    logger.info("Updating VoteOption: " + voteOption.getId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }


    @Override
    public boolean delete(Integer id) {
        boolean success = false;
        if (conn != null) {
            String query = "DELETE FROM VoteOption WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    success = true;
                    logger.info("Deleting VoteOption by ID: " + id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    private void fromRsToVoteOption(ResultSet rs, VoteOption voteOption) throws SQLException {
        voteOption.setId(rs.getInt("id"));
        voteOption.setCaption(rs.getString("caption"));
        voteOption.setPresentationOrder(rs.getInt("presentationOrder"));
        voteOption.setPollId(rs.getInt("poll_id")); // Asegura que pollId se asigna correctamente
        // Assuming the Poll object is set elsewhere or will be fetched based on poll_id if necessary
    }
}
