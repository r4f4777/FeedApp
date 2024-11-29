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
        </nav>
    </header>
    
    <div class="create-poll-container">
        <h1 class="create-polls-header">CREATE A POLL</h1>
        <label for="option1">ADD OPTIONS</label>
        
        <form id="pollOptionsForm" method="post">
            <div class="option-container" id="options">
                <div class="option-wrapper">
                    <span class="checkmark"><img src="img/tick-option.png" alt="tick-option"></span>
                    <input type="text" id="caption" placeholder="Option 1">
                </div>
            </div>
            
            <div class="button-group">
                <button type="button" class="add-option-button" onclick="addOption()">ADD +</button>
                <button type="button" class="create-button" onclick="showSuccessMessage()" id="createButton" style="display: none;">CREATE</button>
            </div>
            
            <input type="hidden" name="poll_id" id="poll_id" value="${poll.id}">
        </form>
        
        <div id="successModal" class="modal">
            <div class="modal-content">
                <h2>Congratulations!</h2>
                <p>Your poll has been successfully published.</p>
                <div class="modal-buttons">
                    <a href="polls.do"><button id="button1">Home</button></a>
                    <a href="CreatePoll.do"><button id="buttonCreatePoll">Create another poll</button></a>
                </div>
            </div>
        </div>
        
        <div id="deleteConfirmModal" class="modal" style="display: none;">
            <div class="modal-content">
                <h2>Are you sure you want to delete this option?</h2>
                <p>This action cannot be undone.</p>
                <div class="modal-buttons">
                    <button id="confirmDeleteButton">Yes, Delete</button>
            		<button id="cancelDeleteButton" onclick="closeDeleteModal()">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <div class="aditional-information">
        <img id="info-container" src="img/info-container.png">
        <div class="info-box">
            <div class="info-content">
                <p id="more-info">MORE INFO</p>
                <p id="information-create-poll">By clicking the "Add" button, you can add as many answers as you want to your poll. Once you've added all the options, press the "Create" button to finish the process.</p>
                <p id="warning-info"><img src="img/warning.png" class="warning-icon">You must add at least one option to create a poll!</p>
            </div>
        </div>
    </div>

    <script>
        let optionCount = 1;
        let optionToDelete = null;

        function addOption() {
            const captionInput = document.getElementById("caption");
            const pollId = document.getElementById("poll_id").value;
            const caption = captionInput.value.trim();

            if (caption === "") {
                alert("Please enter an option.");
                return;
            }

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "CreateVoteOption.do", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    const voteOptionId = response.voteOptionId;
                    
                    const optionsContainer = document.getElementById("options");
                    
                    const newOptionWrapper = document.createElement("div");
                    newOptionWrapper.className = "option-wrapper";
                    newOptionWrapper.setAttribute("data-id", voteOptionId);

                    const checkmark = document.createElement("span");
                    checkmark.className = "checkmark";
                    checkmark.innerHTML = '<img src="img/tick-option.png" alt="tick-option">';

                    const newOptionInput = document.createElement("input");
                    newOptionInput.type = "text";
                    newOptionInput.placeholder = `Option ${optionCount}`;
                    newOptionInput.value = caption;
                    newOptionInput.setAttribute("readonly", true);

                    const deleteBtn = document.createElement("button");
                    deleteBtn.className = "delete-btn";
                    deleteBtn.innerHTML = '<img src="img/delete.png" alt="Delete">';
                    deleteBtn.addEventListener("click", function (e) {
                        e.preventDefault();
                        openDeleteModal(voteOptionId, newOptionWrapper);
                    });

                    newOptionWrapper.appendChild(checkmark);
                    newOptionWrapper.appendChild(newOptionInput);
                    newOptionWrapper.appendChild(deleteBtn);
                    optionsContainer.appendChild(newOptionWrapper);

                    optionCount++;
                    captionInput.value = "";
                    captionInput.placeholder = `Option ${optionCount}`;
                    toggleCreateButton();
                }
            };

            xhr.send("caption=" + encodeURIComponent(caption) + "&poll_id=" + encodeURIComponent(pollId));
        }

        function openDeleteModal(optionId, optionElement) {
            optionToDelete = { id: optionId, element: optionElement };
            document.getElementById("deleteConfirmModal").style.display = "flex";
        }

        function closeDeleteModal() {
            document.getElementById("deleteConfirmModal").style.display = "none";
            optionToDelete = null;
        }

        document.getElementById("confirmDeleteButton").addEventListener("click", function () {
            if (optionToDelete) {
                deleteOption(optionToDelete.id, optionToDelete.element);
                closeDeleteModal();
            }
        });

        function deleteOption(optionId, optionElement) {
            const pollId = document.getElementById("poll_id").value;
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "DeleteVoteOptionServlet.do", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        optionElement.remove();
                        toggleCreateButton();
                    } else {
                        alert("Failed to delete option.");
                    }
                }
            };
            xhr.send("optionId=" + encodeURIComponent(optionId) + "&pollId=" + encodeURIComponent(pollId));
        }

        function toggleCreateButton() {
            const createButton = document.getElementById("createButton");
            const optionsContainer = document.getElementById("options");
            createButton.style.display = optionsContainer.children.length > 1 ? "block" : "none";
        }

        function showSuccessMessage() {
            document.getElementById("successModal").style.display = "flex";
            confetti({ particleCount: 100, spread: 70, origin: { y: 0.6 } });
        }
    </script>
</body>
</html>