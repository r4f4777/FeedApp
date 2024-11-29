package es.unex.pi.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Poll;
import es.unex.pi.model.User;
import es.unex.pi.model.Vote;
import es.unex.pi.model.VoteOption;
import es.unex.pi.dao.JDBCPollDAOImpl;
import es.unex.pi.dao.PollDAO;
import es.unex.pi.dao.JDBCVoteOptionDAOImpl;
import es.unex.pi.dao.VoteOptionDAO;
import es.unex.pi.dao.VoteDAO;
import es.unex.pi.dao.JDBCVoteDAOImpl;

/**
 * Servlet implementation class PollDetailsServlet
 */
@WebServlet("/PollDetailsServlet.do")
public class PollDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PollDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");

        Integer pollId = Integer.parseInt(request.getParameter("pollId"));
        

        PollDAO pollDAO = new JDBCPollDAOImpl();
        pollDAO.setConnection(conn);

        VoteOptionDAO voteOptionDAO = new JDBCVoteOptionDAOImpl();
        voteOptionDAO.setConnection(conn);
        
        VoteDAO voteDAO = new JDBCVoteDAOImpl();
        voteDAO.setConnection(conn);

        Poll poll = pollDAO.getById(pollId);
        List<VoteOption> voteOptions = voteOptionDAO.getAllByPollId(pollId);
        
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
      
        boolean userVote = false;
        Integer selectedVoteOptionId = null;

        if (user != null) {
            Vote existingVote = voteDAO.getByUserAndPollId(user.getId(), pollId);
            if (existingVote != null) {
                userVote = true;
                selectedVoteOptionId = existingVote.getVoteOptionId(); 
            }
        }

        request.setAttribute("userVote", userVote);
        request.setAttribute("poll", poll);
        request.setAttribute("voteOptions", voteOptions);
        request.setAttribute("selectedVoteOptionId", selectedVoteOptionId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/viewPoll.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}