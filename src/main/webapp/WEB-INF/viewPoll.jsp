<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GAFF.com</title>
    <link rel="stylesheet" href="css/vote.css">
    <link rel="icon" href="img/GAFF_LogoNegro(SinFondo).png">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Telegraf:wght@400;700&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.4.0/dist/confetti.browser.min.js"></script>
</head>
<body>
    <div class="background">
        <img src="img/DeWatermark.ai_1730145383315.jpg" alt="background1">
    </div>

    <header>
        <nav>
            <div class="logo">
                <img src="img/GAFF_LogoNegro(SinFondo).png" width="50" alt="Gaff Logo" class="left-logo">
                <h3>GAFF</h3>
            </div>
             <ul class="menu">
                 <li><a href="startingPageServlet.do">HOME</a></li>
                <li><a href="polls.do">POLLS</a></li>
                <li><a href="startingPageServlet.do#statistics">STATISTICS</a></li>
                <li><a href="#">ABOUT US</a></li>
            </ul>
            <div class="auth-buttons">
	                <c:choose>
	                    <c:when test="${not empty user.id}">
	                        <a href="perfilUsuarioServlet.do" class="profile">PROFILE</a>
	                    </c:when>
	                    <c:otherwise>
	                    	<a href="iniciarSesion.do" class="login">SING IN</a>
	                        <a href="registroUsuario.do" class="register">CREATE AN ACCOUNT</a>
	                    </c:otherwise>
	                </c:choose>
	            </div>
        </nav>
    </header>

    <div class="create-poll-container">
        <h1 class="polls-header">POLL DETAILS</h1>
        <label for="question" class="vote-labels">POLL QUESTION</label>
        <input type="text" id="question" placeholder="Pregunta del tema" value="${poll.question}" readonly>

        <label for="options" class="vote-labels">CHOOSE ONE:</label>
        <div class="option-container" id="options">
            <div class="poll-card">
                <div class="poll-options-container">
                    <c:choose>
                        <c:when test="${userVote}">
                            <form action="EditVote.do" method="post" class="vote-form">
                                <div class="poll-options">
                                    <c:forEach var="option" items="${voteOptions}">
                                        <label class="poll-options-label">
                                            <input type="radio" name="voteOptionId" value="${option.id}"
                                                <c:if test="${option.id == selectedVoteOptionId}">checked</c:if>>
                                            ${option.caption}
                                        </label>
                                    </c:forEach>
                                </div>
                                <input type="hidden" name="pollId" value="${poll.id}">
                                <div class="button-group">
                                    <button type="submit" class="vote-option-button">EDIT VOTE
                                        <img src="img/tick-option-white.png" alt="tick-option" id="tick-option">
                                    </button>
                                </div>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="Vote.do" method="post" class="vote-form">
                                <div class="poll-options">
                                    <c:forEach var="option" items="${voteOptions}">
                                        <label class="poll-options-label">
                                            <input type="radio" name="voteOptionId" value="${option.id}" required>
                                            ${option.caption}
                                        </label>
                                    </c:forEach>
                                </div>
                                <input type="hidden" name="pollId" value="${poll.id}">
                                <div id="popup-error" class="popup-error" style="display: none;">
                                    Please select an option to vote
                                </div>
                                <div class="button-group">
                                    <button type="submit" class="vote-option-button" onclick="showSuccessMessage()">VOTE
                                        <img src="img/tick-option-white.png" alt="tick-option" id="tick-option">
                                    </button>
                                </div>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <div id="successModal" class="modal">
            <div class="modal-content">
                <h2>Congratulations!</h2>
                <p>Your has been vote in the poll successfully.</p>
                <div class="modal-buttons">
                    <a href="polls.do"><button id="button1">Home</button></a>
                    <a href="CreatePoll.do"><button id="buttonCreatePoll">Vote in another poll</button></a>
                </div>
            </div>
        </div>

    <div class="aditional-information">
        <img id="info-container" src="img/vote-info2.png">
        <div class="info-box">
            <div class="info-content">
                <p id="more-info">MORE INFO</p>
                <p id="information-create-poll">You will only be able to select a single option to vote. You can only cast one vote per poll, although after submitting it you will be able to modify it.</p>
                <p id="warning-info"><img src="img/warning.png" class="warning-icon">To modify your vote you must go to the polls menu!</p>
            </div>
        </div>
    </div>

    <div class="footer">
        <div class="footer-text">
            <p id="leaveYourEmail"><strong>DON'T FORGET TO LEAVE YOUR EMAIL!</strong></p>
            <p id="stayUpdated">STAY UPDATED ON THE VOTING RESULTS OR ANY CHANGES</p>
        </div>
        <form>
            <input type="email" class="email-input" placeholder="Your email address">
            <button type="submit" class="subscribe-button">SUBSCRIBE!</button>
        </form>
        <div class="footer-image-container">
            <img src="img/logo-footer.png" alt="Footer Background" class="footer-bg-image">
        </div>
    </div>

    <div class="datos">
        <a href="#">ABOUT GAFF.COM</a>
        <a href="#">CUSTOMER SERVICE</a>
        <a href="#">TERMS AND CONDITIONS</a>
        <a href="#">PRIVACY & COOKIES POLICY</a>
    </div>
</body>
</html>
