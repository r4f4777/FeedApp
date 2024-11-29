package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCPollDAOImpl;
import es.unex.pi.dao.PollDAO;

@WebServlet("/DeletePollServlet.do")
public class DeletePollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DeletePollServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        PollDAO pollDAO = new JDBCPollDAOImpl();
        pollDAO.setConnection(conn);

        try {
        	String pollIdParam = request.getParameter("pollId"); 
            int pollId = Integer.parseInt(pollIdParam);
            pollDAO.delete(pollId); 
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
