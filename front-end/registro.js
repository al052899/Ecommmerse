document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('.register-form');

    form.addEventListener('submit', async function(event) {
        event.preventDefault();

        const nombres = document.querySelector('input[placeholder="Nombres *"]').value;
        const apellidos = document.querySelector('input[placeholder="Apellidos *"]').value;
        const username = document.querySelector('input[placeholder="Usuario *"]').value;
        const email = document.querySelector('input[placeholder="Email *"]').value;
        const password = document.querySelector('input[placeholder="Password *"]').value;

        if (!nombres || !apellidos || !username || !email || !password) {
            alert('Todos los campos son obligatorios.');
            return;
        }

        const data = {
            nombres,
            apellidos,
            username,
            email,
            password
        };

        try {
            const response = await fetch('http://localhost:8080/sample-jakarta/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                Swal.fire(result.message).then(() => {
                    window.location.href = 'Login.html';
                });
            } else {
                const errorResult = await response.json();
                Swal.fire('Error: ' + errorResult.message);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error de conexión.');
        }
    });
});
