const contenedorProductos = document.querySelector('.contenedor-grid');

fetch('http://localhost:8080/sample-jakarta/articulos')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(respuesta => {
        console.log('Datos recibidos:', respuesta);

        if (!Array.isArray(respuesta.data)) {
            throw new Error('La propiedad data no es un array');
        }

        respuesta.data.forEach(articulo => {
            console.log('Procesando artículo:', articulo);

            const titulo = articulo.titulo;
            const primeraImagen = articulo.galeriaFotos.split(',')[0].trim();

            const productoDiv = document.createElement('div');
            productoDiv.classList.add('producto');
            productoDiv.setAttribute('data-id', articulo.id);

            productoDiv.innerHTML = `
                <div id="nombre">
                    <p>${titulo}</p>
                </div>
                <div class="main-imagen-tienda">
                    <img id="imagen-producto-tienda" src="${primeraImagen}" alt="${titulo}">
                </div>
                <div id="bottom">
                    <div class="costo-tienda">
                        <p id="precio">$${articulo.precio.toFixed(2)}</p>
                    </div>
                    <div class="añadir-carrito" data-id="${articulo.id}">
                        <img class="imagen-carrito" src="https://i.ibb.co/wcGgLDG/image.png" alt="Añadir al carrito"/>
                    </div>
                </div>
            `;

            const imagenContenedor = productoDiv.querySelector('.main-imagen-tienda');
            imagenContenedor.addEventListener('click', () => {
                window.location.href = `Articulo.html?id=${articulo.id}`;
            });

            const addToCartButton = productoDiv.querySelector('.añadir-carrito img');
            addToCartButton.addEventListener('click', () => {
                const user = JSON.parse(sessionStorage.getItem('user'));
                if (!user) {
                    Swal.fire('Debe iniciar sesión para añadir productos al carrito.');
                    return;
                }

                //acceder al atributo data-id
                const idArticulo = event.target.parentElement.getAttribute('data-id');

                const carritoData = {
                    id_usuario: user.id,
                    cantidad: 1
                };

                fetch(`http://localhost:8080/sample-jakarta/articulos/${idArticulo}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(carritoData)
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
                    Swal.fire('Artículo añadido al carrito correctamente.');
                })
                .catch(error => {
                    Swal.fire(`Error al añadir al carrito: ${error.message}`);
                });
            });

            contenedorProductos.appendChild(productoDiv);
        });
    })
    .catch(error => {
        console.error('Error al obtener los artículos:', error);
    });
