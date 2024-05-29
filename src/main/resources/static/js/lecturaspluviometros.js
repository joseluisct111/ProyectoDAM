// Call the dataTables jQuery plugin
$(document).ready(function () {
    alert('Hola');
    //cargarPluviometros();
    $('#pluviometros').DataTable();
    //actualizarEmailUsuario();
   // obtenerCantidadPluviometros();
    cargarNombresPluviometros()
});

function actualizarEmailUsuario() {
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarPluviometros() {
    const request = await fetch('api/pluviometros', {
        method: 'GET',
        headers: getHeaders(),
    });
    const pluviometros = await request.json();

    let listadoPluviometrosHtml = '';
    for (let pluviometro of pluviometros) {
        let botonEliminar = '<a href="#" onclick="eliminarPluviometro(' + pluviometro.id + ')" class="btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i> </a>'
        let pluviometroHtml = '<tr><td>' + pluviometro.id + '</td> <td>' + pluviometro.nombre + '</td> <td>'
            + pluviometro.latitud + '</td> <td>'
            + pluviometro.longitud + '</td> <td> ' + botonEliminar + ' </td></tr>';
        listadoPluviometrosHtml += pluviometroHtml;
    }
    document.querySelector('#pluviometros tbody').outerHTML = listadoPluviometrosHtml;
}

// Funcion para devolver el headers
function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    }

}
// Función para obtener el número de registros de la tabla
// Call the dataTables jQuery plugin
$(document).ready(function () {
    cargarPluviometros();
    $('#pluviometros').DataTable();
    actualizarEmailUsuario();
});

function actualizarEmailUsuario() {
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarPluviometros() {
    const request = await fetch('api/pluviometros', {
        method: 'GET',
        headers: getHeaders(),
    });
    const pluviometros = await request.json();

    let listadoPluviometrosHtml = '';
    for (let pluviometro of pluviometros) {
        let botonEliminar = '<a href="#" onclick="eliminarPluviometro(' + pluviometro.id + ')" class="btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i> </a>'
        let pluviometroHtml = '<tr><td>' + pluviometro.id + '</td> <td>' + pluviometro.nombre + '</td> <td>'
            + pluviometro.latitud + '</td> <td>'
            + pluviometro.longitud + '</td> <td> ' + botonEliminar + ' </td></tr>';
        listadoPluviometrosHtml += pluviometroHtml;
    }
    document.querySelector('#pluviometros tbody').outerHTML = listadoPluviometrosHtml;
}

// Funcion para devolver el headers
function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    }

}
// Función para obtener el número de registros de la tabla

async function obtenerCantidadPluviometros() {
    const request = await fetch('api/pluviometros/count', {
        method: 'GET',
        headers: getHeaders(),
    });
    const cantidad = await request.json();
    mostrarCantidadPluviometros(cantidad); // Llamar a la función mostrarCantidadPluviometros con la cantidad obtenida
}

function mostrarCantidadPluviometros(cantidad) {
    // Mostrar la cantidad en el HTML
    const cantidadElement = document.getElementById('cantidad-pluviometros');
    if (cantidadElement) {
        cantidadElement.textContent = cantidad.toString();
    }
}

async function eliminarPluviometro(id) {
    if (!confirm('¿Está seguro de eliminar el pluviómetro?')) {
        return;
    }
    const request = await fetch('api/pluviometros/' + id, {
        method: 'DELETE',
        headers: getHeaders()
    });
    location.reload();
}


async function eliminarPluviometro(id) {
    if (!confirm('¿Está seguro de eliminar el pluviómetro?')) {
        return;
    }
    const request = await fetch('api/pluviometros/' + id, {
        method: 'DELETE',
        headers: getHeaders()
    });
    location.reload();
}


async function cargarNombresPluviometros() {
    const request = await fetch('api/pluviometros', {
        method: 'GET',
        headers: getHeaders(),
    });
    const pluviometros = await request.json();

    let optionsHtml = '';
    for (let pluviometro of pluviometros) {
        optionsHtml += `<option value="${pluviometro.id}">${pluviometro.nombre}</option>`;
    }

    $('#selectPluviometros').html(optionsHtml);
}
