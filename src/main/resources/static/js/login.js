// Call the dataTables jQuery plugin
$(document).ready(function() {
    // on Ready
});

async function IniciarSesion() {
    let datos = {

        email: document.getElementById('txtEmail').value,
        password  : document.getElementById('txtPassword').value,
       };

    const request = await fetch('api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    const respuesta = await request.text();

    if(respuesta != 'fail') {
        localStorage.setItem('token', respuesta);
        localStorage.setItem('email', datos.email);
        window.location.href = 'index.html';
    } else {
        var mensaje = 'Usuario o contraseña incorrectos';
        var titulo = 'Error de autenticación';

        document.title = titulo;
        alert(mensaje);
    }

}