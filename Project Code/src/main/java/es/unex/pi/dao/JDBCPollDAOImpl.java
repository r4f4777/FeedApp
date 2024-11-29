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
import es.unex.pi.model.Poll;

public class JDBCPollDAOImpl implements PollDAO {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(JDBCPollDAOImpl.class.getName());

    @Override
    public Poll get(String question) {
        if (conn == null) return null;

        Poll poll = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM polls WHERE question = '" + question + "'");
            if (!rs.next()) return null;

            poll = new Poll();
            fromRsToPollObject(rs, poll);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return poll;
    }
    
    @Override
    public Poll getById(Integer id) {
        if (conn == null) return null;

        Poll poll = null;
        String query = "SELECT * FROM polls WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                poll = new Poll();
                fromRsToPollObject(rs, poll); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return poll;
    }


    @Override
    public List<Poll> getAll() {
        if (conn == null) return null;

        List<Poll> polls = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM polls");
            while (rs.next()) {
                Poll poll = new Poll();
                fromRsToPollObject(rs, poll);
                polls.add(poll);
                logger.info("Fetching poll: " + poll.getQuestion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return polls;
    }

    @Override
    public Integer add(Poll poll) {
    	Integer id = -1;
        if (conn != null) {
            try {
                String sql = "INSERT INTO polls (question, publishedAt, validUntil, user_id) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, poll.getQuestion());
                stmt.setTimestamp(2, Timestamp.from(poll.getPublishedAt()));
                stmt.setTimestamp(3, Timestamp.from(poll.getValidUntil()));
                stmt.setLong(4, poll.getUserId());
                stmt.executeUpdate();

                Statement selectStmt = conn.createStatement();
                ResultSet rs = selectStmt.executeQuery("SELECT last_insert_rowid() AS id");
                if (rs.next()) {
                    id = rs.getInt("id");
                }

                stmt.close();
                selectStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }


    @Override
    public boolean update(Poll poll) {
        boolean done = false;
        if (conn != null) {
            try {
                String sql = "UPDATE polls SET question = ?, publishedAt = ?, validUntil = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, poll.getQuestion());
                stmt.setTimestamp(2, Timestamp.from(poll.getPublishedAt()));
                stmt.setTimestamp(3, Timestamp.from(poll.getValidUntil()));
                stmt.setInt(4, poll.getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    done = true;
                    logger.info("Updated Poll with ID: " + poll.getId());
                }
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return done;
    }


    @Override
    public boolean delete(Integer id) {
        boolean done = false;
        if (conn != null) {
            try {
                String sql = "DELETE FROM polls WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    done = true;
                }
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return done;
    }


    private void fromRsToPollObject(ResultSet rs, Poll poll) throws SQLException {
        poll.setId(rs.getInt("id"));
        poll.setQuestion(rs.getString("question"));

        long publishedAtMillis = rs.getLong("publishedAt");
        long validUntilMillis = rs.getLong("validUntil");
        
        poll.setPublishedAt(Instant.ofEpochMilli(publishedAtMillis));
        poll.setValidUntil(Instant.ofEpochMilli(validUntilMillis));
        
        poll.setUserId(rs.getInt("user_id"));
    }


    @Override
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}

