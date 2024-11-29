document.addEventListener("DOMContentLoaded", function() {
    const settingsButton = document.querySelector('.settings-button');
    const settingsIcon = document.querySelector('.settings-icon');
    const dropdownMenu = document.querySelector('.dropdown-menu');
    const logoutButton = document.querySelector('.logout-button');
    const logoutModal = document.getElementById('logoutModal');
    const confirmLogoutButton = document.getElementById('confirmLogout');
    const cancelLogoutButton = document.getElementById('cancelLogout');

    // Mostrar u ocultar el menú desplegable al hacer clic en el botón de ajustes
    settingsButton.addEventListener('click', function(event) {
        event.stopPropagation(); // Evita que el clic se propague al document
        dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
        
        // Añadir la animación de rotación al hacer clic en el botón de ajustes
        settingsIcon.classList.add('rotate');
        setTimeout(() => {
            settingsIcon.classList.remove('rotate');
        }, 500); // Duración de la animación de rotación (en milisegundos)
    });

    // Cierra el menú desplegable si se hace clic fuera de él
    document.addEventListener('click', function() {
        dropdownMenu.style.display = 'none';
    });

    // Evita cerrar el menú al hacer clic dentro de él
    dropdownMenu.addEventListener('click', function(event) {
        event.stopPropagation();
    });

    // Mostrar el modal al hacer clic en "Cerrar Sesión"
    logoutButton.addEventListener('click', function() {
        logoutModal.style.display = 'flex';
    });

    // Ocultar el modal y redirigir a perfil al confirmar
    confirmLogoutButton.addEventListener('click', function() {
        logoutModal.style.display = 'none';
        window.location.href = 'profile.html'; // Vuelve al perfil sin cerrar sesión realmente
    });

    // Ocultar el modal al cancelar
    cancelLogoutButton.addEventListener('click', function() {
        logoutModal.style.display = 'none';
    });

    // Funciones para abrir y cerrar el modal de edición de encuestas
    function openEditPollModal() {
        document.getElementById('editPollModalWindow').style.display = 'flex';
    }

    function closeEditPollModal() {
        document.getElementById('editPollModalWindow').style.display = 'none';
    }

    // Event listeners para los botones de editar encuestas
    document.querySelectorAll('.edit-button').forEach(button => {
        button.addEventListener('click', openEditPollModal);
    });
    
    
    

    // Botón para cerrar el modal de edición
    document.querySelector('#editPollModalWindow .modal-buttons button:last-child').addEventListener('click', closeEditPollModal);

    // Cerrar el modal si se hace clic fuera de la ventana modal
    window.addEventListener('click', function(event) {
        if (event.target === logoutModal) {
            logoutModal.style.display = 'none';
        }
        if (event.target === document.getElementById('editPollModalWindow')) {
            closeEditPollModal();
        }
    });
    
    
    
});
