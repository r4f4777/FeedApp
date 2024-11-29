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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import es.unex.pi.util.MongoDBConnection;
/**
 * Servlet implementation class registroUsuario
 */
@WebServlet(
		urlPatterns = { "/registroUsuario.do" } 
		)
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if (session.getAttribute("order")!=null) session.removeAttribute("order");
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/register.jsp"); 
		view.forward(request,response);	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		String name = request.getParameter("name");
		
		User user = new User();
		
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setUsername(request.getParameter("username"));
		
		
		Map<String, String> messages = new HashMap<String, String>();
		
		List<User> userlist = new ArrayList();
		userlist = userDAO.getAll();
		
		for(int i = 0; i < userlist.size(); i++) {
			User u = userlist.get(i);
			if(user.getEmail().equals(u.getEmail())) {
				messages.put("error", "Error, this mail is already owned by another user");
				request.getRequestDispatcher("/WEB-INF/registroUsuario.jsp").forward(request, response);
			}
		}
		
		if (user.validate(messages) && user!=null) {
			HttpSession session = request.getSession();
			Integer userId  = userDAO.add(user);
			user.setId(userId);
			
            try (MongoDBConnection mongoDB = new MongoDBConnection()) {
                mongoDB.registerUser(user);
            }
			
			session.removeAttribute("user");
			session.setAttribute("user",user);
			user = null;
			response.sendRedirect("startingPageServlet.do");
		} 
		else {
			request.setAttribute("messages",messages);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/bookingRegistrado.jsp");
			view.forward(request,response);
		}	
	}

}