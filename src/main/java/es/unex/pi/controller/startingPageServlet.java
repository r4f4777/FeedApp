package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import es.unex.pi.dao.JDBCPollDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.JDBCVoteDAOImpl;
import es.unex.pi.model.Poll;
import es.unex.pi.model.User;
import es.unex.pi.model.Vote;
import es.unex.pi.dao.PollDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.VoteDAO;

/**
 * Servlet implementation class paginaPrincipal
 */
@WebServlet("/startingPageServlet.do")
public class startingPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public startingPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	     PollDAO pollDAO = new JDBCPollDAOImpl();
	     UserDAO userDAO = new JDBCUserDAOImpl();
	     VoteDAO voteDAO = new JDBCVoteDAOImpl();
	     pollDAO.setConnection(conn);
	     userDAO.setConnection(conn);
	     voteDAO.setConnection(conn);
	        
		 List<Poll> polls = pollDAO.getAll(); 
	     List<Vote> votes = voteDAO.getAll(); 
	     List<User> users = userDAO.getAll(); 

	    request.setAttribute("pollCount", polls.size());
	    request.setAttribute("voteCount", votes.size());
	    request.setAttribute("userCount", users.size());
	        
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
