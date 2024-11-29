// Función para añadir una nueva opción de votación
function addOption() {
    const optionsContainer = document.getElementById('options'); // Contenedor de opciones

    // Crear un nuevo contenedor para la opción
    const newOptionWrapper = document.createElement('div');
    newOptionWrapper.className = 'option-wrapper';

    // Agregar el icono de checkmark
    const checkmark = document.createElement('span');
    checkmark.className = 'checkmark';
    checkmark.innerHTML = '<img src="img/tick-option.png" alt="tick-option">';

    // Crear el campo de texto para la nueva opción
    const newOption = document.createElement('input');
    newOption.type = 'text';
    newOption.name = 'option';
    newOption.placeholder = `Opción ${optionsContainer.children.length + 1}`;

    // Crear el botón para eliminar la opción
    const deleteBtn = document.createElement('button');
    deleteBtn.className = 'delete-btn';
    deleteBtn.innerHTML = '<img src="img/delete.png" alt="Eliminar">';
    
    // Evento para eliminar esta opción al hacer clic en la papelera
    deleteBtn.addEventListener('click', function() {
        optionsContainer.removeChild(newOptionWrapper); // Remueve la opción al hacer clic en eliminar
    });

    // Ensamblar los elementos en el nuevo contenedor de opción
    newOptionWrapper.appendChild(checkmark);
    newOptionWrapper.appendChild(newOption);
    newOptionWrapper.appendChild(deleteBtn);
    
    // Añadir la nueva opción al contenedor de opciones
    optionsContainer.appendChild(newOptionWrapper);
}


// Función para mostrar el modal de éxito y lanzar confeti
function showSuccessMessage(event) {
    // Evita el envío del formulario hasta que verifiquemos la selección
    event.preventDefault();

    // Selecciona todas las opciones de voto
    const options = document.querySelectorAll('input[name="voteOptionId"]');
    let selected = false;

    // Verifica si alguna opción está seleccionada
    options.forEach(option => {
        if (option.checked) {
            selected = true;
        }
    });

    // Obtener el popup de error
    const popupError = document.getElementById("popup-error");

    // Si no hay ninguna opción seleccionada, muestra el popup de error y ocúltalo después de 3 segundos
    if (!selected) {
        popupError.style.display = "block"; // Muestra el popup de error

        // Oculta el popup después de 3 segundos
        setTimeout(() => {
            popupError.style.display = "none";
        }, 3000);

        return; // Detiene la ejecución para que no se abra el modal ni se envíe el formulario
    }

    // Si hay una opción seleccionada, oculta el mensaje de error, muestra el modal de éxito y lanza el confeti
    popupError.style.display = "none"; // Oculta el mensaje de error si todo es correcto
    document.getElementById("successModal").style.display = "flex";
    confetti({
        particleCount: 100,
        spread: 70,
        origin: { y: 0.6 }
    });

    // Envía el formulario después de mostrar el popup de éxito
    setTimeout(() => {
        event.target.form.submit();
    }, 1000); // Da un segundo para mostrar el popup antes de enviar el formulario
}





// Función para redirigir a la página de inicio
function goHome() {
    window.location.href = "index.html"; // Cambia 'index.html' al enlace de la página de inicio
}

// Función para redirigir a la creación de una nueva encuesta
function createAnotherPoll() {
    window.location.href = "createPoll.html"; // Cambia 'create_poll.html' al enlace de crear otra encuesta
}







