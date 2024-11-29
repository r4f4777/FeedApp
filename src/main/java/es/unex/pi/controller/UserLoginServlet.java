package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.System.Logger;
import java.sql.Connection;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class iniciarSesion
 */

@WebServlet("/iniciarSesion.do")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			response.sendRedirect("paginaPrincipal.do");
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/login.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    
	    Connection conn = (Connection) getServletContext().getAttribute("dbConn");

	    UserDAO userDAO = new JDBCUserDAOImpl();
	    userDAO.setConnection(conn); 

	    User user = userDAO.getByEmail(email);

	    if (user != null && user.getPassword().equals(password)) {
	        HttpSession session = request.getSession();
	        session.setAttribute("user", user);
	        response.sendRedirect("startingPageServlet.do"); 
	    } else {
	        request.setAttribute("errorMessage", "Incorrect email or password.");
	        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/login.jsp");
	        view.forward(request, response);
	    }
	}

}
