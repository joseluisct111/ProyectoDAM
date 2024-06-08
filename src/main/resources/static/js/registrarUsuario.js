// Inicializa el plugin de jQuery dataTables cuando el documento está listo
$(document).ready(function() {
    // on Ready
});

// Función asíncrona para registrar un usuario
async function registrarUsuario() {
    // Recoge los valores de los campos de entrada del formulario y los almacena en un objeto
    let datos = {
        nombre: document.getElementById('txtNombre').value,
        apellido  : document.getElementById('txtApellidos').value,
        email: document.getElementById('txtEmail').value,
        password  : document.getElementById('txtPassword').value,
        administrador: document.getElementById('chkAdministrador').checked
    };

    // Recoge el valor del campo de entrada 'Repetir Contraseña'
    let repetirPassword = document.getElementById('txtRepetirPassword').value;

    // Comprueba si la contraseña y la repetición de la contraseña son iguales
    if  (datos.password != repetirPassword) {
        // Si las contraseñas no coinciden, muestra una alerta al usuario y detiene la ejecución de la función
        alert('Las contraseñas no coinciden');
        return;
    }

    // Si las contraseñas coinciden, realiza una solicitud POST a la API de usuarios con los datos del formulario en formato JSON
    const request = await fetch('api/usuarios', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    // Redirige al usuario a la página de inicio de sesión
    window.location.href = '/inicio';
}

