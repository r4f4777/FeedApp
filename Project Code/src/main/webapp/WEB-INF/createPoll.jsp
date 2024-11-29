<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>GAFF.com</title>
        <link rel="stylesheet" href="css/createPoll.css">
        <link rel="icon" href="img/GAFF_LogoNegro(SinFondo).png">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Telegraf:wght@400;700&display=swap" rel="stylesheet">

        <script src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.4.0/dist/confetti.browser.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

        <script src="js/createPoll.js"></script>
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
	     <form action="CreatePoll.do" method="post">
	        <h1 class="create-polls-header">CREATE A POLL</h1>
	        <label for="question">ADD THE QUESTION</label>
	        <input type="text" id="question" name="question" required placeholder="Write the question here">
	
	        <label for="valid-until">VALID UNTIL:</label>
	        <div id="valid-until" class="flatpickr-container">
	        	<input type="datetime-local" id="validUntil" name="validUntil" required>
	        </div>
			<input type="hidden" id="user_id" name="user_id" value="${user.id}">
	        <div class="button-group">
	            <button class="add-option-button-title" type="submit">ADD OPTIONS +</button>
	        </div>
        </form>
    </div>
    
    <div id="successModal" class="modal">
            <div class="modal-content">
                <h2>¡Congrats!</h2>
                <p>You have created a poll, and it has been successfully published.</p>
                <div class="modal-buttons">
                    <button onclick="goHome()">Home</button>
                    <button onclick="createAnotherPoll()">Create another poll</button>
                </div>
            </div>
    </div>

    </div>
    <div class="aditional-information">
        <img id="info-container" src="img/info-container.png">
        <div class="info-box">
            <div class="info-content">
                <p id="more-info">MORE INFO</p>
                <p id="information-create-poll">By clicking the add button, you can enter as many responses as you wish in your survey. Once you have all the questions added, press the create button to complete the process.</p>
                <p id="warning-info"><img src="img/warning.png" class="warning-icon">To create a survey, you must add at least one option!</p>
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