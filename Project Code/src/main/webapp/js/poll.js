function showMorePolls() {
    // Selecciona todas las encuestas que están ocultas
    const hiddenPolls = document.querySelectorAll('.poll-card.hidden');
    
    // Muestra todas las encuestas ocultas
    hiddenPolls.forEach(poll => {
        poll.classList.remove('hidden');
        poll.classList.add('visible');
    });

    // Oculta el botón "Show More" después de mostrar todas las encuestas
    document.getElementById('showMoreButton').style.display = 'none';
}