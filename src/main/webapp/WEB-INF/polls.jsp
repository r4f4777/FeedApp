<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PollApp.com | OfficialWebsite</title>
    <link rel="stylesheet" href="css/polls.css">
    <script src="js/poll.js"></script>
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
    
    
    
    <div class="polls-container">
    <h1 class="polls-header">POLLS</h1>

    
    <c:set var="pollCount" value="0" />

    <c:forEach var="poll" items="${polls}">
        <c:if test="${pollCount < 3}">
          
            <div class="poll-card visible">
                <h2 class="poll-title">${poll.question}</h2>
                <div class="poll-details">
                    <div class="poll-dates">
                        <span>CREATED ON: ${poll.publishedAt}</span>
                        <span>EXPIRES ON: ${poll.validUntil}</span>
                    </div>
                    <div class="poll-action">
                        <span>CLICK HERE TO VOTE&nbsp;&nbsp;&nbsp;IN <br> THE POLL</span>
                    </div>
                </div>
                <div class="poll-link-container">
                    <a href="PollDetailsServlet.do?pollId=${poll.id}" class="poll-link">VIEW POLL</a>
                    <img src="img/lupa.png" alt="Lupa" class="poll-icon">
                </div>
            </div>
        </c:if>
        <c:if test="${pollCount >= 3}">
          
            <div class="poll-card hidden">
                <h2 class="poll-title">${poll.question}</h2>
                <div class="poll-details">
                    <div class="poll-dates">
                        <span>CREATED ON: ${poll.publishedAt}</span>
                        <span>EXPIRES ON: ${poll.validUntil}</span>
                    </div>
                    <div class="poll-action">
                        <span>CLICK HERE TO VOTE&nbsp;&nbsp;&nbsp;IN <br> THE POLL</span>
                    </div>
                </div>
                <div class="poll-link-container">
                    <a href="PollDetailsServlet.do?pollId=${poll.id}" class="poll-link">VIEW POLL</a>
                    <img src="img/lupa.png" alt="Lupa" class="poll-icon">
                </div>
            </div>
        </c:if>
        
        <c:set var="pollCount" value="${pollCount + 1}" />
    </c:forEach>

   
    <button id="showMoreButton" onclick="showMorePolls()">Show More</button>
</div>




    
     <div class="creatorPoll-container">
	    <div class="flex-container">
	        <img src="img/bell_create.png" alt="bell_create" class="bell_create">
	        <p class="poll-text">DON'T FORGET, YOU CAN CREATE<br>YOUR OWN POLLS!</p>
	
	        <c:choose>
	            <c:when test="${not empty user.id}">
	               
	                <div>
	                    <a href="CreatePoll.do"><button class="create-poll-btn">CREATE YOUR POLL</button></a>
	                </div>
	            </c:when>
	            <c:otherwise>
	               
	                <div>
	                    <a href="registroUsuario.do"><button class="create-poll-btn">REGISTER NOW</button></a>
	                </div>
	            </c:otherwise>
	        </c:choose>
	
	        <img src="img/stars.png" alt="stars" class="stars">
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
        <a href="#">MANAGE PERSONALIZED RECOMMENDATIONS</a>
    </div>
  
   
</body>
</html>
