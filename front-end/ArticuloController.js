class ArticuloController {
    constructor() {
        this.articleId = this.getArticleIdFromUrl();
        this.galeriaElement = document.querySelector('.galeria');
        this.imagenArticuloElement = document.querySelector('#imagen-articulo');
        this.descripcionArticuloElement = document.querySelector('.descripcion-articulo');
        this.costoArticuloElement = document.querySelector('.costo-articulo');
        this.especificacionesArticuloElement = document.querySelector('.especificaciones-articulo');
        this.addToCartButton = document.querySelector('.añadir-carrito img');
    }

    getArticleIdFromUrl() {
        const params = new URLSearchParams(window.location.search);
        return params.get('id');
    }

    createCarousel(images) {
        const galeria = this.galeriaElement;
        galeria.innerHTML = images.map((img, index) => `
            <img src="${img.trim()}" class="carousel-image ${index === 0 ? 'active' : ''}" alt="Imagen ${index + 1}">
        `).join('');

        let currentIndex = 0;
        setInterval(() => {
            const totalImages = images.length;
            galeria.querySelector('.carousel-image.active').classList.remove('active');
            currentIndex = (currentIndex + 1) % totalImages;
            galeria.querySelectorAll('.carousel-image')[currentIndex].classList.add('active');
        }, 3000);

        galeria.querySelectorAll('.carousel-image').forEach((img, index) => {
            img.addEventListener('click', () => {
                this.changeMainImage(images[index]);
            });
        });
    }

    changeMainImage(imageSrc) {
        this.imagenArticuloElement.innerHTML = `<img src="${imageSrc.trim()}" alt="Imagen Principal">`;
    }

    loadArticleData() {
        fetch(`http://localhost:8080/sample-jakarta/articulos/${this.articleId}`)
            .then(response => response.json())
            .then(data => {
                this.imagenArticuloElement.innerHTML = `<img src="${data.data.galeriaFotos.split(',')[0].trim()}" alt="${data.data.titulo}">`;
                this.descripcionArticuloElement.innerHTML = `<p>${data.data.titulo}</p>`;
                this.costoArticuloElement.innerHTML = `<p>$${data.data.precio.toFixed(2)}</p>` + `<p>Cantidad disponible: ${data.data.cantidadDisponible}</p>`;
                this.especificacionesArticuloElement.innerHTML = `<p>${data.data.especificaciones}</p><br><p>${data.data.descripcion}</p>`;

                this.createCarousel(data.data.galeriaFotos.split(','));

                // Añadir evento click para añadir al carrito
                this.addToCartButton.addEventListener('click', () => {
                    const user = JSON.parse(sessionStorage.getItem('user'));
                    if (!user) {
                        Swal.fire('Debe iniciar sesión para añadir productos al carrito.');
                        return;
                    }

                    const carritoData = {
                        id_usuario: user.id, // Usa 'id_usuario' en lugar de 'idUsuario'
                        cantidad: 1
                    };

                    fetch(`http://localhost:8080/sample-jakarta/articulos/${this.articleId}`, {
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
                        Swal.fire('Artículo añadido al carrito correctamente.').then(()=>{
                            location.reload(true) 
                        })
                    })
                    .catch(error => {
                        Swal.fire(`Error al añadir al carrito: ${error.message}`);
                    });
                });
            })
            .catch(error => console.error('Error al cargar los datos del artículo:', error));
    }

    init() {
        if (this.articleId) {
            this.loadArticleData();
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const articuloController = new ArticuloController();
    articuloController.init();
});
