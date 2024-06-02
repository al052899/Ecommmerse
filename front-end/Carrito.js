document.addEventListener('DOMContentLoaded', () => {
    const user = JSON.parse(sessionStorage.getItem('user'));
    if (!user) {
        Swal.fire('Debe iniciar sesión para ver el carrito.');
        return;
    }

    fetch(`http://localhost:8080/sample-jakarta/carrito`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: user.id })
    })
    .then(response => response.json())
    .then(data => {
        const contenedorCarrito = document.querySelector('.contenedor-carrito');
        contenedorCarrito.innerHTML = '';

        if (data.data.length === 0) {
            contenedorCarrito.innerHTML = '<p>El carrito está vacío.</p>';
            return;
        }

        data.data.forEach(articulo => {
            const articuloDiv = document.createElement('div');
            articuloDiv.classList.add('articulo-carrito');

            articuloDiv.innerHTML = `
                <div class="nombre-articulo">
                    <p>${articulo.titulo}</p>
                </div>
                <div class="imagen-articulo-carrito">
                    <img src="${articulo.galeriaFotos.split(',')[0].trim()}" alt="${articulo.titulo}">
                </div>
                <div class="costo-articulo-carrito">
                    <p>$${articulo.precio.toFixed(2)}</p>
                </div>
                <div class="cantidad-articulo-carrito">
                    <p>Cantidad: ${articulo.cantidad}</p>
                </div>
            `;

            contenedorCarrito.appendChild(articuloDiv);
        });
    })
    .catch(error => console.error('Error al cargar el carrito:', error));
});
