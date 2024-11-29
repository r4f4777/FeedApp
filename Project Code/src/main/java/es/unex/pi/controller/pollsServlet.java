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
import es.unex.pi.model.Poll;
import es.unex.pi.dao.PollDAO;

/**
 * Servlet implementation class paginaPrincipal
 */
@WebServlet("/polls.do")
public class pollsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pollsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        
        PollDAO pollDAO = new JDBCPollDAOImpl();
        pollDAO.setConnection(conn);

        List<Poll> polls = pollDAO.getAll();  
        request.setAttribute("polls", polls);

        request.getRequestDispatcher("/WEB-INF/polls.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
