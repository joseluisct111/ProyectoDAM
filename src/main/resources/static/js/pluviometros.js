// Call the dataTables jQuery plugin
$(document).ready(function () {
    cargarPluviometros();

    // Agregar evento de cambio al checkbox
    $('#chkMostrarInactivos').change(function () {
        cargarPluviometros();
    });
});



async function cargarPluviometros() {
    const request = await fetch('api/pluviometros', {
        method: 'GET',
        headers: getHeaders(),
    });
    let pluviometros = await request.json();

    // Obtener el estado del checkbox
    const mostrarInactivos = $('#chkMostrarInactivos').is(':checked');

    // Filtrar los pluviometros si el checkbox no está marcado
    if (!mostrarInactivos) {
        pluviometros = pluviometros.filter(pluviometro => pluviometro.activo);
    }

    let listadoPluviometrosHtml = '';

    for (let pluviometro of pluviometros) {
        let botonModificar = '<a href="/modificarpluvi?id=' + pluviometro.id + '" class="btn btn-warning btn-icon-split"> <span class="text">Modificar</span> <span class="icon text-white-50"> <i class="fas fa-edit"></i> </span> </a>';
        let colorActivo = pluviometro.activo ? 'green' : 'red';

        let pluviometroHtml = '<tr><td>' + pluviometro.id + '</td> <td>' + pluviometro.nombre + '</td> <td>'
            + pluviometro.latitud + '</td> <td>'
            + pluviometro.longitud + '</td> <td style="background-color: ' + colorActivo + '; color: white; font-weight: bold; text-align: center;"> '
            + pluviometro.activo + '</td> <td> '
            + botonModificar+ ' </td></tr>';
        listadoPluviometrosHtml += pluviometroHtml;
    }
    document.querySelector('#pluviometros tbody').outerHTML = listadoPluviometrosHtml;
    $('#pluviometros').DataTable();
}

// Funcion para devolver el headers
function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.token
    }

}




// Funcion para devolver el headers

// Función para obtener el número de registros de la tabla





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
    try {
        const request = await fetch('api/pluviometros', {
            method: 'GET',
            headers: getHeaders(),
        });
        const pluviometros = await request.json();


        // Limpiar el combobox
        document.getElementById('selectPluviometros').innerHTML = '';

        // Agregar opciones al combobox
        pluviometros.forEach(pluviometro => {
            const option = document.createElement('option');
            option.value = pluviometro.id;
            option.textContent = pluviometro.nombre;
            document.getElementById('selectPluviometros').appendChild(option);
        });
    } catch (error) {
        console.error('Error al cargar los nombres de los pluviómetros:', error);
    }
}

