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
                    <li><a href="polls.do" class="active">POLLS</a></li>
                    <li><a href="startingPageServlet.do#statistics">STATISTICS</a></li>
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
        </header>

        <div class="create-poll-container">
            <form method="post" action="EditPoll.do">
                <h1 class="create-polls-header">EDIT OPTIONS</h1>
                <input type="hidden" name="id" value="${poll.id}">

                <label for="question">YOUR QUESTION:</label>
                <input type="text" name="question" id="question" value="${poll.question}" required>

                <label for="valid-until">VALID UNTIL:</label>
                <div id="valid-until" class="flatpickr-container">
                    <input type="datetime-local" name="validUntil" id="validUntil" value="${validUntilFormatted}" required>
                </div>

                <label>OPTION CAPTION:</label>

                <c:forEach var="option" items="${voteOptions}">
                    <div class="option-wrapper">
                        <input type="hidden" name="optionId" value="${option.id}">
                        <span class="checkmark"><img src="img/tick-option.png" alt="tick-option"></span>

                        <input type="text" name="caption${option.id}" id="caption-${option.id}" value="${option.caption}" required>

                        <button type="button" class="delete-option-button" onclick="openDeleteOptionModal(${option.id})" style="border: none; background: none;">
                            <img src="${pageContext.request.contextPath}/img/delete.png" alt="Delete ${option.id}">
                        </button>
                    </div>
                </c:forEach>

                <div class="button-group">
                    <button type="button" class="add-option-button" id="add-option-edit" onclick="addOption()">ADD OPTION</button>
                    <button type="submit" class="create-button" id="save-changes-edit">SAVE CHANGES</button>
                </div>
            </form>
        </div>

        <div id="deleteOptionConfirmModal" class="modal" style="display: none;">
            <div class="modal-content">
                <h2>Are you sure you want to delete this option?</h2>
                <p>This action cannot be undone.</p>
                <div class="modal-buttons">
                    <button id="confirmDeleteOptionButton" class="confirmDeleteButton">Yes, delete</button>
                    <button id="cancelDeleteOptionButton" onclick="closeDeleteOptionModal()">Cancel</button>
                </div>
            </div>
        </div>
        
		<div id="addOptionModal" class="modal" style="display: none;">
		    <div class="modal-content">
		        <h2>Add New Option</h2>
		        <p>Enter the text for the new option:</p>
		        <input type="text" id="newOptionCaption" placeholder="New option text" required>
		        <div class="modal-buttons">
		            <button id="confirmAddOptionButton" class="confirmAddButton">Add Option</button>
		            <button id="cancelAddOptionButton" onclick="closeAddOptionModal()">Cancel</button>
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

        <script>
            let optionIdToDelete = null;

            function openDeleteOptionModal(optionId) {
                optionIdToDelete = optionId;
                document.getElementById("deleteOptionConfirmModal").style.display = "flex";
            }

            function closeDeleteOptionModal() {
                document.getElementById("deleteOptionConfirmModal").style.display = "none";
                optionIdToDelete = null;
            }

            document.getElementById("confirmDeleteOptionButton").addEventListener("click", function () {
                if (optionIdToDelete !== null) {
                    deleteVoteOption(optionIdToDelete);
                    closeDeleteOptionModal();
                }
            });

            function deleteVoteOption(optionId) {
                const xhr = new XMLHttpRequest();
                xhr.open("POST", "DeleteVoteOptionServlet.do", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            window.location.reload(); 
                        } else {
                            alert("Error al eliminar la opción.");
                        }
                    }
                };
                xhr.send("optionId=" + encodeURIComponent(optionId));
            }
            
            function addOption() {
                document.getElementById("addOptionModal").style.display = "flex";
            }

            function closeAddOptionModal() {
                document.getElementById("addOptionModal").style.display = "none";
                document.getElementById("newOptionCaption").value = "";
            }

            function confirmAddOption() {
                const pollId = document.querySelector("input[name='id']").value;
                const newOptionCaption = document.getElementById("newOptionCaption").value;

                if (newOptionCaption) {
                    const xhr = new XMLHttpRequest();
                    xhr.open("POST", "AddVoteOptionServlet.do", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4) {
                            if (xhr.status === 200) {
                                window.location.reload(); 
                            } else {
                                alert("Error al agregar la nueva opción.");
                            }
                        }
                    };

                    xhr.send("pollId=" + encodeURIComponent(pollId) + "&caption=" + encodeURIComponent(newOptionCaption));

                    closeAddOptionModal();
                } else {
                    alert("Please enter text for the new option.");
                }
            }

            document.getElementById("confirmAddOptionButton").addEventListener("click", confirmAddOption);

        </script>
    </body>
</html>
