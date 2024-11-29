<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>GAFF.com</title>
        <link rel="stylesheet" href="css/login.css">
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
                <li><a href="startingPageServlet.do">HOME</a></li>
                <li><a href="polls.do">POLLS</a></li>
                <li><a href="startingPageServlet.do#statistics">STATISTICS</a></li>
                <li><a href="#">ABOUT US</a></li>
            </ul>
        </nav>
    </header>
    <div class="login-container">
	        <h2>SIGN IN <img src="img/signInLogo.png" alt="sign-in-logo" class="sign-in-logo"></h2>
	        <c:if test="${not empty errorMessage}">
		        <div class="error-message">
		           <p class="error-message">${errorMessage}</p>
		        </div>
		    </c:if>
	        <form class="login-form" action="iniciarSesion.do" method="post">
	            <input type="email" id="email" name="email" placeholder="Email Address" required>
	            <input type="password" id="password" name="password" placeholder="Password" required>
	            <button type="submit">LOGIN</button>
	            <div class="separator-line">
	                <span class="separator-text">or use one of these options</span>
	            </div>
	            <div id="registration-options">
	                <div class="registration-option"><img src="img/icono_facebook.png" width="30" alt="Facebook Icon"></div>
	                <div class="registration-option"><img src="img/icono_google.png" width="30" alt="Google Icon"></div>
	                <div class="registration-option"><img src="img/icono_apple.png" width="30" alt="Apple Icon"></div>
	            </div>
	            <div class="separator"></div>
	        </form>
	        <p class="register-text">New to GAFF? <a href="registroUsuario.do">Register now</a></p>
	        <p class="terms">By signing in or creating an account, you accept our <a href="#">Terms & Conditions</a> and <a href="#">Privacy Policy</a>.</p>
	        <footer>© GAFF 2024. All rights reserved.</footer>
	    </div>
	</body>
</html>
