<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Nacho Alcalde">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GAFF.com</title>
    <link rel="stylesheet" href="css/styleFeedApp.css">
    <link rel="icon" href="img/GAFF_LogoNegro(SinFondo).png">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Telegraf:wght@400;700&display=swap" rel="stylesheet">
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
                <li><a href="startingPageServlet.do" class="active">HOME</a></li>
                <li><a href="polls.do">POLLS</a></li>
                <li><a href="#statistics">STATISTICS</a></li>
                <li><a href="#">ABOUT US</a></li>
            </ul>
            <div class="auth-buttons">
                <c:choose>
                    <c:when test="${not empty user.id}">
                        <a href="perfilUsuarioServlet.do" class="profile">PROFILE</a>
                    </c:when>
                    <c:otherwise>
                    	<a href="iniciarSesion.do" class="login">Sing in</a>
                        <a href="registroUsuario.do" class="register">Create an account</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
        <div class="header-content">
            <img src="img/campana (2).png" alt="belt-image" class="bell-icon">
            <h1>Vote now!</h1>
            <p class="subtitle">"Find your poll, make your choice"</p>
            <p class="description">Explore topics, share your opinion, and see what others think. Every vote matters. Start making a difference today!</p>
            <img src="img/message.png" alt="message-image" class="message-icon">
            <a href="polls.do">
                <button class="start-voting">START VOTING</button>
            </a>
        </div>
        <div class="baners">
            <div class="baners-up">
                <img src="img/latestPolls.png" alt="right-up">
                <img src="img/stayUpdated.png" alt="mid-up">
                <img src="img/makeAnImpact.png" alt="left-up">
            </div>
            
            <div class="baners-down">
                <img src="img/Categories.png" alt="right-up">
                <div class="start-creating-container">
                    <img src="img/createAPoll.png" alt="left-up">
                    	 <c:choose>
	            			<c:when test="${not empty user.id}">
		                        <a href="CreatePoll.do">
		                        	<button class="start-creating">START CREATING</button>
		                        </a>
		                    </c:when>
				            <c:otherwise>
				                <div>
				                    <a href="registroUsuario.do"><button class="start-creating">REGISTER NOW</button></a>
				                </div>
				            </c:otherwise>
				        </c:choose>
                </div>
            </div>
        </div>
    </header>
    
    <div class="container">   
        <div class="decoracion">
            <img src="img/decoracion.png" alt="decoracion">
        </div>
        <div class="black-bar">
            <img src="img/app_movil.png" alt="App Móvil" class="app-movil-img">
            <div class="text-container">
                <div class="text-align-right">
                    <p>Now With Your Favorite Poll Mobile Application In Your Pocket</p>
                </div>
                <div class="app-store-links">
                    <div class="store-link">
                        <img src="img/appleStore.png" alt="App Store Icon" class="store-icon">
                        <p class="store-text">Get it on the<br><strong>App Store</strong></p>
                    </div>
                    <div class="store-link">
                        <img src="img/googlePlay.png" alt="Google Play Icon" class="store-icon">
                        <p class="store-text">Get it on<br><strong>Google Play</strong></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="main-section">
            <div class="social-section">
                <h2 class="social-title">SOCIAL</h2>
                <div class="social-posts">
                    <img src="img/RedesSociales.png" alt="social posts">
                </div>
            </div>
        
            <div class="statistics-section" id="statistics">
                <h2 class="statistics-title">STADISTICS</h2>
                
                <div class="statistics-top-card">
                    <div class="statistics-text">
                        <p class="statistics-number">${pollCount}</p>
                        <p class="statistics-label">POLLS CREATED</p>
                    </div>
                    <img src="img/pollsCreated.png" alt="Polls Icon">
                </div>
                
                <div class="statistics-bottom-cards">
                    <div class="statistics-card">
                        <div class="statistics-text">
                            <p class="statistics-number">${voteCount}</p>
                            <p class="statistics-label">VOTES</p>
                        </div>
                        <img src="img/Votes.png" alt="Votes Icon">
                    </div>
                    <div class="statistics-card">
                        <div class="statistics-text">
                            <p class="statistics-number">${userCount}</p>
                            <p class="statistics-label">USERS</p>
                        </div>
                        <img src="img/Users.png" alt="Users Icon">
                    </div>
                </div>
            </div>
        </div>
        <div class="yellow-bar">
            <div class="yellow-bar-item">
                <img src="img/GAFF_LogoNegro(SinFondo).png" alt="GAFF Icon" class="yellow-bar-icon gaaf-logo"> 
                <span class="gaff-text">GAFF</span>
            </div>
            <div class="yellow-bar-item">
                <img src="img/calltelephoneauricularincircularbutton_80086.png" alt="Phone Icon" class="yellow-bar-icon"> 
                <span class="contact-text">+34 647892595</span> 
            </div>
            <div class="yellow-bar-item">
                <img src="img/location_pin_place_map_address_placeholder_icon_149098.png" alt="Address Icon" class="yellow-bar-icon"> 
                <span class="contact-text">Inndalsveien 28, 5063 Bergen</span> 
            </div>
            <div class="yellow-bar-item social-icons">
                <img src="img/Twitter_Rounded_icon-icons.com_61577.png" alt="Icon 1" class="yellow-bar-icon"> 
                <img src="img/communication_message_socialmedia_chat_telegram_icon_218852.png" alt="Icon 2" class="yellow-bar-icon"> 
                <img src="img/youtube_icon-icons.com_65437.png" alt="Icon 3" class="yellow-bar-icon"> 
            </div>
        </div>
        <footer class="footer">
            <div class="footer-content">
                <div class="footer-section about-us">
                    <h3>ABOUT US:</h3>
                    <ul>
                        <li>Ignacio Alcalde Torrescusa</li>
                        <li>Darío Álvarez Barrado</li>
                        <li>Carlos Fernández Calderón</li>
                        <li>Rafael Guiberteau Tinoco</li>
                    </ul>
                </div>
                
                <div class="footer-section course-site-map">
                    <div class="course">
                        <h3>COURSE</h3>
                        <p>DAT250 | FINAL PROJECT</p>
                    </div>
                    <div class="site-map">
                        <h3>SITE MAP</h3>
                        <p><img src="img/location_pin_place_map_address_placeholder_icon_149098(white).png" alt="Location Icon" class="location-icon"> Inndalsveien 28, 5063 Bergen</p>
                    </div>
                </div>
                <div class="footer-image-container">
                    <img src="img/logo-footer.png" alt="Footer Background" class="footer-bg-image">
                </div>
            </div>
            <div class="footer-bottom">
                <p>© GAFF 2024. All rights reserved.</p>
            </div>
        </footer>
        
        
    </div>
</body>
</html>
