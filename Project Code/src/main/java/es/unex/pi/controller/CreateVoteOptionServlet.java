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
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.VoteOption;
import es.unex.pi.model.Poll;
import es.unex.pi.model.User;
import es.unex.pi.dao.JDBCVoteOptionDAOImpl;
import es.unex.pi.dao.VoteOptionDAO;
import es.unex.pi.dao.JDBCPollDAOImpl;
import es.unex.pi.dao.PollDAO;

@WebServlet("/CreateVoteOption.do")
public class CreateVoteOptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CreateVoteOptionServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        VoteOptionDAO voteOptionDAO = new JDBCVoteOptionDAOImpl();
        voteOptionDAO.setConnection(conn);

        Poll poll = (Poll) session.getAttribute("poll");

        if (poll != null) {
            List<VoteOption> voteOptions = voteOptionDAO.getAllByPollId(poll.getId());
            request.setAttribute("voteOptions", voteOptions);
        }
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/createOptions.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");

        VoteOptionDAO voteOptionDAO = new JDBCVoteOptionDAOImpl();
        voteOptionDAO.setConnection(conn);
        
        Poll poll = (Poll) session.getAttribute("poll");
        Integer pollId = poll.getId();
        String caption = request.getParameter("caption");
        int presentationOrder = voteOptionDAO.getAllByPollId(pollId).size() + 1;

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("iniciarSesion.do");
            return;
        }

        VoteOption voteOption = new VoteOption();
        voteOption.setCaption(caption);
        voteOption.setPresentationOrder(presentationOrder);
        voteOption.setPollId(pollId);

        Integer voteOptionId = voteOptionDAO.add(voteOption);
        if (voteOptionId == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write("{\"voteOptionId\":" + voteOptionId + ", \"caption\":\"" + caption + "\"}");
        }
    }

}
