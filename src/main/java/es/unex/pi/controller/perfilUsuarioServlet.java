package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCVoteDAOImpl;
import es.unex.pi.dao.JDBCPollDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.VoteDAO;
import es.unex.pi.dao.PollDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Vote;
import es.unex.pi.model.Poll;
import es.unex.pi.model.User;

/**
 * Servlet implementation class perfilUsuarioServlet
 */
@WebServlet("/perfilUsuarioServlet.do")
public class perfilUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public perfilUsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	    
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	    HttpSession session = request.getSession(false); 
	    
	    VoteDAO voteDAO = new JDBCVoteDAOImpl();
	    voteDAO.setConnection(conn);
	    
	    PollDAO pollDAO = new JDBCPollDAOImpl();
	    pollDAO.setConnection(conn);
	    
	    try {
		   
		    if (session != null && session.getAttribute("user") != null) {
		       
		        User user = (User) session.getAttribute("user");
		        
		        List<Poll> pollList = pollDAO.getAll();
		        request.setAttribute("user", user);
		        request.setAttribute("pollList", pollList);
		        
		    } 
		    
		   
		    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/userProfile.jsp");
		    view.forward(request, response);
	    
	    } catch (Exception e) {
            logger.severe("Exception: " + e.getMessage());
            
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
