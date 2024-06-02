document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!email || !password) {
        alert("Email y contraseña son obligatorios.");
        return;
    }

    const loginData = {
        email: email,
        password: password
    };

    fetch('http://localhost:8080/sample-jakarta/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData),
        credentials: 'include'
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(data => {
                throw new Error(data.message);
            });
        }
        return response.json();
    })
    .then(data => {
        if (data.code === 200) {
            // Guarda la información del usuario en sessionStorage
            sessionStorage.setItem('user', JSON.stringify({ id: data.data.id, usuario: data.data.email, username:data.data.username })); // Asegúrate de que data.data.id es el id del usuario
            // Redirige a la página de la tienda
            window.location.href = "Tienda.html";
        } else {
            Swal.fire("Error de autenticación: " + data.message);
        }
    })
    .catch(error => {
        console.error("Error:", error);
        Swal.fire("Error al iniciar sesión: " + error.message);
    });
});
