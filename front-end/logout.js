document.getElementById('logout').addEventListener('click', function() {
  Swal.fire({
      title: '¿Segur@ que desea cerrar sesión?',
      showDenyButton: true,
      confirmButtonText: `Sí`,
      denyButtonText: `No`,
  }).then((result) => {
      if (result.isConfirmed) {
          // Lógica de cierre de sesión
          sessionStorage.removeItem('user'); // Eliminar la información del usuario de sessionStorage
          Swal.fire('Sesión cerrada correctamente', '', 'success').then(() => {
              window.location.href = 'Login.html'; // Redirigir a la página de inicio de sesión
          });
      } else if (result.isDenied) {
          Swal.fire('Cierre de sesión cancelado', '', 'info');
      }
  });
});
