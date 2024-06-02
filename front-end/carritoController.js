//declaracion de variables
let contenedorDeCarrito;
let botonComprar;
const user = sessionStorage.getItem('user');
const userObj = JSON.parse(user);
const userId = userObj.id;

//funcion init
function init(){
    contenedorDeCarrito = document.querySelector('.Cuerpo-compras');
    botonComprar = document.getElementById('realizar-compra');
    obtenerArticulos();

    botonComprar.addEventListener('click', comprarArticulos);
}

//hacer solicitudes a la API
function obtenerArticulos(){
    fetch(`http://localhost:8080/sample-jakarta/carrito?id_usuario=${userId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        data.data.forEach(articulo => {
            // Crear los artículos
            verArticulos(articulo);
            calcularTotalInit()
        });

        // Ocultar el contenedor del carrito vacío si hay productos en el carrito
        const carritoVacio = document.querySelector('.carrito-vacio');
        if (data.data.length > 0) {
            carritoVacio.style.display = 'none';
        } else {
            carritoVacio.style.display = 'flex'; // Ajusta la visualización según tu diseño
        }
    })
    .catch(error => console.error('Error:', error));
}

function verArticulos(articulo){
    let precioIndividual = articulo.precio * articulo.cantidad

    var html = `
        <div class="productos-carrito">
            <div class="main-foto-carrito">
                <div id="imagen-carrito">
                    <img src=${articulo.galeria_fotos.split(',')[0]}>
                </div>
            </div>
            <div class="datos">
                <div class="eliminar">
                <button id="eliminar-articulo" data-id="${articulo.id}">X</button>
                </div>
                <div class="descripcion-carrito" data-id="${articulo.id_articulo}">${articulo.titulo}</div>
                <div class="divisor"></div>
                <div class="funciones-carrito">
                    <label for="cantidad-carrito" id="label-carrito">Cantidad:</label>
                    <div id="cantidad-carrito"> 
                        <p id="cantidad" data-id="${articulo.id}">${articulo.cantidad}</p>
                    </div>
                    <img id="aumentar-carrito" src="https://i.ibb.co/gmBPJ3X/add-shopping-cart-24dp-FILL0-wght400-GRAD0-opsz24.png" alt="add" data-id="${articulo.id}"/>                    
                    <img id="quitar-carrito" src="https://i.ibb.co/MGxfH6w/delete.png" alt="delete" data-id="${articulo.id}"/>
                </div>
                <div class="divisor"></div>
                <div class="costo-carrito">${precioIndividual.toLocaleString('es-MX', { style: 'currency', currency: 'MXN' })}</div>
            </div>
        </div>
    `;
    contenedorDeCarrito.innerHTML += html;

    /**
     * PUT
     */
    //AUMENTAR
    document.querySelectorAll('#aumentar-carrito').forEach(boton =>{
        boton.addEventListener('click', (event) => {
            const id = parseInt(boton.dataset.id);
            let elementoCarrito = event.target.closest('.productos-carrito')
            let id_articulo = parseInt(elementoCarrito.querySelector('.descripcion-carrito').dataset.id)
            let cantidad = elementoCarrito.querySelector('#cantidad')
            
            fetch(`http://localhost:8080/sample-jakarta/carrito/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "id_usuario": userId,
                    "id_articulo":id_articulo,
                    "cantidad":1
                })
            })
            .then(response => response.json())
            .then(data => {
                console.log(data)
                if(data.code == 200){
                    cantidad.textContent = data.data.cantidadCarrito
                    let precioIndividual = data.data.precioArticulo * data.data.cantidadCarrito
                    elementoCarrito.querySelector('.costo-carrito').textContent = precioIndividual.toLocaleString('es-MX', { style: 'currency', currency: 'MXN' })
                    calcularTotal()
                }else{
                    Swal.fire(data.message)
                }
            })
            .catch(error => console.error('Error:', error))
        })
    })
    //DISMINUIR
    document.querySelectorAll('#quitar-carrito').forEach(boton =>{
        boton.addEventListener('click', (event) => {
            const id = parseInt(boton.dataset.id);
            let elementoCarrito = event.target.closest('.productos-carrito')
            let id_articulo = parseInt(elementoCarrito.querySelector('.descripcion-carrito').dataset.id)
            let cantidad = elementoCarrito.querySelector('#cantidad')
            
            fetch(`http://localhost:8080/sample-jakarta/carrito/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "id_usuario": userId,
                    "id_articulo":id_articulo,
                    "cantidad":-1
                })
            })
            .then(response => response.json())
            .then(data => {
                console.log(data)
                if(data.code == 200){
                    cantidad.textContent = data.data.cantidadCarrito
                    let precioIndividual = data.data.precioArticulo * data.data.cantidadCarrito
                    elementoCarrito.querySelector('.costo-carrito').textContent = precioIndividual.toLocaleString('es-MX', { style: 'currency', currency: 'MXN' })
                    calcularTotal()
                }else{
                    Swal.fire(data.message)
                }
            })
            .catch(error => console.error('Error:', error))
        })
    })
    /**
     * DELETE
     */
    document.querySelectorAll('#eliminar-articulo').forEach(boton =>{
        boton.addEventListener('click', (event) => {
            const id = parseInt(boton.dataset.id);
            let elementoCarrito = event.target.closest('.productos-carrito')
            let id_articulo = parseInt(elementoCarrito.querySelector('.descripcion-carrito').dataset.id)
            let cantidad = parseInt(elementoCarrito.querySelector('#cantidad').textContent)
            
            fetch(`http://localhost:8080/sample-jakarta/carrito/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "id_usuario": userId,
                    "id_articulo": id_articulo,
                    "cantidad": cantidad
                })
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if(data.code == 200){
                    location.reload(true);
                }else{
                    Swal.fire(data.message);
                }
            })
            .catch(error => console.error('Error:', error));
        });
    });
}

function comprarArticulos(){
    fetch(`http://localhost:8080/sample-jakarta/carrito`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "id_usuario": userId
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        if(data.code == 200){
            Swal.fire(data.message).then(() => {
                location.reload(true);
            });
        }else{
            Swal.fire(data.message);
        }
    })
    .catch(error => console.error('Error:', error));
}

function calcularTotal() {
    let totalCompra = 0;
    let articulosCarrito = document.querySelectorAll('.productos-carrito');

    articulosCarrito.forEach(articulo => {
        let precio = parseFloat(articulo.querySelector('.costo-carrito').textContent.replace(/[^0-9.-]+/g,""));
        totalCompra += precio;
    });

    document.getElementById('total-compra').textContent = `Total: $${totalCompra.toFixed(2)}`;
}

function calcularTotalInit() {
    let totalCompra = 0;
    let articulosCarrito = document.querySelectorAll('.productos-carrito');

    articulosCarrito.forEach(articulo => {
        let precio = parseFloat(articulo.querySelector('.costo-carrito').textContent.replace(/[^0-9.-]+/g,""));
        let cantidad = parseInt(articulo.querySelector('#cantidad').textContent);
        totalCompra += precio * cantidad;
    });

    document.getElementById('total-compra').textContent = `Total: $${totalCompra.toFixed(2)}`;
}
//listener para generar todo lo necesario cuando se haya cargado el DOM
window.addEventListener('load', init);