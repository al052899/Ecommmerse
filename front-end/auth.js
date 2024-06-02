document.addEventListener('DOMContentLoaded', function() {
    const user = sessionStorage.getItem('user');
    if (!user) {
        window.location.href = "Login.html";
    } else {
        const userObj = JSON.parse(user);
        const saludoElement = document.getElementById('saludo');
        if (saludoElement) {
            saludoElement.textContent = `¡Bienvenid@, ${userObj.username}! Tu ID de usuario es ${userObj.id}.`;
        }
    }
});
