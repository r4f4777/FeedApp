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
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCVoteDAOImpl;
import es.unex.pi.dao.VoteDAO;
import es.unex.pi.model.Vote;
import es.unex.pi.model.User;
import es.unex.pi.model.VoteOption;

@WebServlet("/EditVote.do")
public class EditVoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EditVoteServlet.class.getName());

    public EditVoteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");

        VoteDAO voteDAO = new JDBCVoteDAOImpl();
        voteDAO.setConnection(conn);

        try {
            User sessionUser = (User) session.getAttribute("user");
            int userId = sessionUser.getId();
            int pollId = Integer.parseInt(request.getParameter("pollId"));

            Vote userVote = voteDAO.getByUserAndPollId(userId, pollId);
            if (userVote != null) {
                request.setAttribute("userVote", userVote);
                RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/editVote.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("PollDetailsServlet.do?pollId=" + pollId);
            }
        } catch (NumberFormatException e) {
            logger.warning("Invalid poll ID format");
            response.sendRedirect("paginaPrincipal.do");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");

        VoteDAO voteDAO = new JDBCVoteDAOImpl();
        voteDAO.setConnection(conn);

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser != null) {
            int userId = sessionUser.getId();
            int pollId = Integer.parseInt(request.getParameter("pollId"));
            int voteOptionId = Integer.parseInt(request.getParameter("voteOptionId"));

            Vote existingVote = voteDAO.getByUserAndPollId(userId, pollId);
            if (existingVote != null) {
                existingVote.setVoteOptionId(voteOptionId);
                existingVote.setPublishedAt(java.time.Instant.now()); 

                voteDAO.update(existingVote);
            } else {
                Vote newVote = new Vote();
                newVote.setUser(sessionUser);
                newVote.setVoteOptionId(voteOptionId);
                newVote.setPublishedAt(java.time.Instant.now());

                voteDAO.add(newVote);
            }

            request.getRequestDispatcher("polls.do").forward(request, response);
        } else {
            response.sendRedirect("login.do"); 
        }
    }
}
