package es.unex.pi.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.time.Instant;
import java.util.logging.Logger;

import es.unex.pi.model.Vote;
import es.unex.pi.model.User;
import es.unex.pi.model.VoteOption;
import es.unex.pi.util.MongoDBConnection;
import es.unex.pi.dao.VoteDAO;
import es.unex.pi.dao.JDBCVoteDAOImpl;

/**
 * Servlet implementation class VoteServlet
 */
@WebServlet("/Vote.do")
public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(VoteServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");

        VoteDAO voteDAO = new JDBCVoteDAOImpl();
        voteDAO.setConnection(conn);

        Integer voteOptionId = Integer.parseInt(request.getParameter("voteOptionId"));
        Integer pollId = Integer.parseInt(request.getParameter("pollId"));

        HttpSession session = request.getSession(false); 
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        Vote vote = new Vote();
        vote.setPublishedAt(Instant.now()); 
        vote.setVoteOptionId(voteOptionId);
        vote.setPollId(pollId);

        if (user != null) {
            vote.setUser(user);
        } 

        long voteId = voteDAO.add(vote);
        if (voteId != -1) {
            
            try (MongoDBConnection mongoDB = new MongoDBConnection()) {
                mongoDB.registerVote(pollId, voteOptionId, user != null ? user.getId() : null);
            } catch (Exception e) {
                logger.warning("Failed to register vote in MongoDB: " + e.getMessage());
            }
        } 
        
        request.getRequestDispatcher("polls.do").forward(request, response);
    }

}
