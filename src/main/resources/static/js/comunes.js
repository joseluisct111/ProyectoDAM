// Definir la función para actualizar el correo electrónico del usuario
function actualizarEmailUsuario() {
    // Verificar si el valor está disponible en localStorage y es válido
    if (sessionStorage && sessionStorage.email) {
        // Seleccionar el elemento HTML para actualizar
        var elementoEmailUsuario = document.getElementById('txt-email-usuario');
        // Verificar si se ha encontrado el elemento
        if (elementoEmailUsuario) {
            // Asignar el contenido del localStorage al elemento
            elementoEmailUsuario.textContent = sessionStorage.email;

        } else {
            console.error('Elemento con ID "txt-email-usuario" no encontrado.');
        }
    } else {
        console.error('Valor de correo electrónico no encontrado en localStorage.');
    }
}

// Llamar a la función al cargar la página
window.onload = function() {

    actualizarEmailUsuario();

};
// Definir la función para cerrar sesión y borrar los datos del localStorage
function cerrarSesion() {
    // Eliminar los datos guardados del usuario en localStorage
    sessionStorage.removeItem('email');
    sessionStorage.removeItem('token');
    // Redirigir a la página de inicio de sesión u otra página relevante
    window.location.href = '/';
}
let chart;





