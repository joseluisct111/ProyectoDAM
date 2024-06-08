// Call the dataTables jQuery plugin
$(document).ready(function() {
    // on Ready
});
function validarCampos() {
    let email = document.getElementById('txtEmail').value;
    let password = document.getElementById('txtPassword').value;

    if (!email || !password) {
        alert('Por favor, rellena todos los campos.');
    } else {
        IniciarSesion();
    }
}
async function IniciarSesion() {
    let datos = {
        email: document.getElementById('txtEmail').value,
        password: document.getElementById('txtPassword').value,
    };

    const request = await fetch('/api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    const respuesta = await request.json();

    if(respuesta.mensaje != 'fail') {
        sessionStorage.setItem('token', respuesta.token);
        sessionStorage.setItem('email', datos.email);
        sessionStorage.setItem('administrador', respuesta.administrador); // Aquí se accede al valor de administrador desde la respuesta de la API
        window.location.href = '/inicio';
    } else {
        var mensaje = 'Usuario o contraseña incorrectos';
        var titulo = 'Error de autenticación';

        document.title = titulo;
        alert(mensaje);
    }
}