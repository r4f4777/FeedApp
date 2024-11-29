<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GAFF.com</title>
    <link rel="stylesheet" href="css/profile.css">
    <link rel="icon" href="img/GAFF_LogoNegro(SinFondo).png">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">

    <script src="js/profile.js"></script>
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
                <a href="perfilUsuarioServlet.do" class="profile">PROFILE</a>

                <div class="settings-container">
                    <button class="settings-button">
                        <img src="img/settings.png" alt="Settings" class="settings-icon"> 
                    </button>
                    
                    
                    <div class="dropdown-menu">
                       		<button class="logout-button">Log out</button>
                    </div>
                </div>        
            </div>
            <div class="modal" id="logoutModal">
                <div class="modal-content">
                    <h3>DO YOU REALLY WANT TO LOG OUT?</h3>
                    <div class="modal-buttons">
                    	<a href="closeSesionServlet.do">
                        	<button id="confirmLogout">CONFIRM</button>
                        </a>
                        <button id="cancelLogout">CANCEL</button>
                    </div>
                </div>
            </div>
        </nav>
    </header>

    <div class="main-content">
        <aside class="sidebar">
            <div class="profile-user">
                <img src="img/profile-photo.png" alt="Profile Picture">
                <h4>${user.username}</h4>
                <p>${user.email}</p>
            </div>
            
            <div class="calendar">
                <h4>Nov 2020</h4>
                <div class="dates">
                   
                    <p><strong>Mon</strong></p>
                    <p><strong>Tue</strong></p>
                    <p><strong>Wed</strong></p>
                    <p><strong>Thu</strong></p>
                    <p><strong>Fri</strong></p>
                    <p><strong>Sat</strong></p>
                    <p><strong>Sun</strong></p>
                    
                    
                    <p></p><p></p><p></p>
                    
                 
                    <p>1</p><p>2</p><p>3</p><p>4</p><p>5</p><p>6</p><p>7</p>
                    <p>8</p><p>9</p><p class="active">10</p><p>11</p><p>12</p><p>13</p><p>14</p>
                    <p>15</p><p>16</p><p>17</p><p>18</p><p>19</p><p>20</p><p>21</p>
                    <p>22</p><p>23</p><p>24</p><p>25</p><p>26</p><p>27</p><p>28</p>
                    <p>29</p><p>30</p>
                </div>
            </div>
            
            <div class="online-users">
                <h4>Online Friends <span>See all ></span></h4>
                <ul>
                    <li><img src="img/online-friends1.png" alt="User"><p>Maren Maureen</p></li>
                    <li><img src="img/online-friends2.png" alt="User"><p>Jennifer Jane</p></li>
                    <li><img src="img/online-friends3.png" alt="User"><p>Ryan Herwinds</p></li>
                    <li><img src="img/online-friends4.png" alt="User"><p>Kierra Culhane</p></li>
                </ul>
            </div>
        </aside>
        
       
    <div class="main-content">
        <div class="courses">
            <div class="filters">
                <span>Filter by:</span>
                <button>Time</button>
                <button>Level</button>
                <button>Language</button>
                <button>Type</button>
            </div>

           
            <c:forEach var="poll" items="${pollList}">
                <c:if test="${poll.userId == user.id}">
                
                    <div class="course-card" data-id="${poll.id}">
                        <img src="img/course-icon.png" class="course-icon" alt="Course Icon">
                        <div class="poll-dates">
                            <h2>${poll.question}</h2>
                            <p>Created on: <span class="date">${poll.publishedAt}</span></p>
                            <p>Expires on: <span class="date">${poll.validUntil}</span></p>
                        </div>
                        <div class="button-group">
                            <button class="delete-button" onclick="openDeletePollModal(${poll.id})">
                                Delete <img src="img/delete-white.png" alt="Delete Icon" class="button-icon">
                            </button>
                            <a href="EditPoll.do?id=${poll.id}">
                                <button class="edit-button">
                                    Edit <img src="img/edit.png" alt="Edit Icon" class="button-icon">
                                </button>
                            </a>
                        </div>
                        <span>Created by Mark Lee</span>
                        <i class="icon arrow"></i>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
    </div>

   
    <div id="deleteConfirmModal" class="modal" style="display: none;">
        <div class="modal-content">
            <h2>Sure you want to delete this survey?</h2>
            <p>This action cannot be undone.</p>
            <div class="modal-buttons">
                <button id="confirmDeletePollButton" class="confirmDeleteButton">Yes, delete</button>
				<button id="cancelDeleteButton" onclick="closeDeletePollModal()">Cancel</button>
            </div>
        </div>
    </div>

    <script>
        let pollIdToDelete = null;

        function openDeletePollModal(pollId) {
            pollIdToDelete = pollId; // Guardamos el ID de la encuesta a eliminar
            document.getElementById("deleteConfirmModal").style.display = "flex";
        }

        function closeDeletePollModal() {
            document.getElementById("deleteConfirmModal").style.display = "none";
            pollIdToDelete = null; // Reseteamos el ID
        }

        document.getElementById("confirmDeletePollButton").addEventListener("click", function () {
            if (pollIdToDelete !== null) {
                deletePoll(pollIdToDelete);
                closeDeletePollModal();
            }
        });

        function deletePoll(pollId) {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "DeletePollServlet.do", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        window.location.reload(); // Recarga la página después de eliminar
                    } else {
                        alert("Error al eliminar la encuesta.");
                    }
                }
            };
            xhr.send("pollId=" + encodeURIComponent(pollId));
        }
    </script>
</body>
</html>
