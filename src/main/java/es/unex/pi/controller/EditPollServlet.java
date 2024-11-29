package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCPollDAOImpl;
import es.unex.pi.dao.PollDAO;
import es.unex.pi.dao.VoteOptionDAO;
import es.unex.pi.dao.JDBCVoteOptionDAOImpl;
import es.unex.pi.model.Poll;
import es.unex.pi.model.VoteOption;

@WebServlet("/EditPoll.do")
public class EditPollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EditPollServlet.class.getName());

    public EditPollServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        
        PollDAO pollDAO = new JDBCPollDAOImpl();
        pollDAO.setConnection(conn);

        VoteOptionDAO voteOptionDAO = new JDBCVoteOptionDAOImpl();
        voteOptionDAO.setConnection(conn);

        try {
            int pollId = Integer.parseInt(request.getParameter("id"));
            Poll poll = pollDAO.getById(pollId);

            if (poll != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                String validUntilFormatted = formatter.format(poll.getValidUntil().atZone(ZoneId.systemDefault()));

                List<VoteOption> voteOptions = voteOptionDAO.getAllByPollId(pollId);

                request.setAttribute("poll", poll);
                request.setAttribute("validUntilFormatted", validUntilFormatted);
                request.setAttribute("voteOptions", voteOptions);

                RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/editPollAndOptions.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("paginaPrincipal.do");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("paginaPrincipal.do");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        
        PollDAO pollDAO = new JDBCPollDAOImpl();
        pollDAO.setConnection(conn);

        VoteOptionDAO voteOptionDAO = new JDBCVoteOptionDAOImpl();
        voteOptionDAO.setConnection(conn);

        int pollId = Integer.parseInt(request.getParameter("id"));
        Poll poll = pollDAO.getById(pollId);

        if (poll != null) {
            poll.setQuestion(request.getParameter("question"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String validUntilStr = request.getParameter("validUntil");
            LocalDateTime validUntilDateTime = LocalDateTime.parse(validUntilStr, formatter);
            Instant validUntil = validUntilDateTime.atZone(ZoneId.systemDefault()).toInstant();
            poll.setValidUntil(validUntil);

            pollDAO.update(poll);

            List<VoteOption> voteOptions = voteOptionDAO.getAllByPollId(pollId);
            for (VoteOption option : voteOptions) {
                String caption = request.getParameter("caption" + option.getId());
                if (caption == null || caption.isEmpty()) {
                    System.out.println("Error: Caption cannot be null or empty for Option ID: " + option.getId());
                    continue; 
                }

                option.setCaption(caption);
                voteOptionDAO.update(option);
            }

            response.sendRedirect("perfilUsuarioServlet.do");
        } else {
            response.sendRedirect("paginaPrincipal.do");
        }
    }

}
