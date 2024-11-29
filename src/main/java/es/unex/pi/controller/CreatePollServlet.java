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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Logger;

import es.unex.pi.model.Poll;
import es.unex.pi.model.User;
import es.unex.pi.util.MongoDBConnection;
import es.unex.pi.dao.JDBCPollDAOImpl;
import es.unex.pi.dao.PollDAO;

/**
 * Servlet implementation class CreatePoll
 */
@WebServlet("/CreatePoll.do")
public class CreatePollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CreatePollServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("poll") != null) {
            session.removeAttribute("poll");
         }
        
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/createPoll.jsp");
        view.forward(request, response);
	}

	
	

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    
    PollDAO pollDAO = new JDBCPollDAOImpl();
    pollDAO.setConnection(conn);
    
    String question = request.getParameter("question");
    String publishedAtStr = request.getParameter("publishedAt");
    String validUntilStr = request.getParameter("validUntil");

    Instant publishedAt = Instant.now();

    LocalDateTime validUntilDateTime = LocalDateTime.parse(validUntilStr);
    Instant validUntil = validUntilDateTime.atZone(ZoneId.systemDefault()).toInstant();

    User user = (User) session.getAttribute("user");
    
    if (user == null) {
        response.sendRedirect("iniciarSesion.do");
        return;
    }
    
    Poll poll = new Poll();
    poll.setQuestion(question);
    poll.setPublishedAt(publishedAt);
    poll.setValidUntil(validUntil);
    Integer user_id = user.getId();
    poll.setUserId(user_id);
    Integer pollId = pollDAO.add(poll);
    poll.setId(pollId);
    session.setAttribute("poll", poll);
    
    try (MongoDBConnection mongoDB = new MongoDBConnection()) {
        mongoDB.registerPoll(pollId, user.getId(), question);
    } catch (Exception e) {
        e.printStackTrace();
    }
    response.sendRedirect("CreateVoteOption.do");
}


}
