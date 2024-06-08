// Call the dataTables jQuery plugin
$(document).ready(function () {
    cargarUsuarios()

    //actualizarEmailUsuario();
});



async function cargarUsuarios() {

    const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: getHeaders(),
    });
    const usuarios = await request.json();

    let listadoUsuariosHtml = '';
    for (let usuario of usuarios) {
        let botonEliminar1 =  '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')"><img src="img/lapiz.png" alt="Eliminar" style="width: 30px; height: 30px;"></a>'
        let botonEliminar = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i> </a>'
        let telefonoTexto = usuario.telefono ? usuario.telefono : '-';
        let usuariohtml = '<tr><td>' + usuario.id + '</td> <td>' + usuario.nombre + '</td> <td>'
            + usuario.email + '</td> <td>'
            + telefonoTexto + '</td> <td> '
            + usuario.administrador + '</td> <td> '
            + botonEliminar + '&nbsp;&nbsp;&nbsp;' + botonEliminar1 + ' </td></tr>';
        listadoUsuariosHtml += usuariohtml;
    }
    document.querySelector('#usuarios tbody').outerHTML = listadoUsuariosHtml;


}

// Funcion para devolver el headers
function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.token
    }
}


async function eliminarUsuario(id) {
    if (!confirm('¿Está seguro de eliminar el usuario?')) {
        return;
    }
    const request = await fetch('api/usuario/' + id, {
        method: 'DELETE',
        headers: getHeaders()
    });
    location.reload();
}