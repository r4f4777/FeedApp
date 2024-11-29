package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.VoteOptionDAO;
import es.unex.pi.dao.JDBCVoteOptionDAOImpl;

@WebServlet("/DeleteVoteOptionServlet.do")
public class DeleteVoteOptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DeleteVoteOptionServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        VoteOptionDAO voteOptionDAO = new JDBCVoteOptionDAOImpl();
        voteOptionDAO.setConnection(conn);

        try {
            String optionIdParam = request.getParameter("optionId");
            int optionId = Integer.parseInt(optionIdParam); 

            voteOptionDAO.delete(optionId);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

