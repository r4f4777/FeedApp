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
import es.unex.pi.model.VoteOption;

@WebServlet("/AddVoteOptionServlet.do")
public class AddVoteOptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AddVoteOptionServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        VoteOptionDAO voteOptionDAO = new JDBCVoteOptionDAOImpl();
        voteOptionDAO.setConnection(conn);

        try {
            String pollIdParam = request.getParameter("pollId");
            String caption = request.getParameter("caption");

            int pollId = Integer.parseInt(pollIdParam);

            VoteOption newOption = new VoteOption();
            newOption.setPollId(pollId);
            newOption.setCaption(caption);

            voteOptionDAO.add(newOption);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
