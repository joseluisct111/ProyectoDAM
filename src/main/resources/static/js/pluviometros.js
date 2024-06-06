// Call the dataTables jQuery plugin
$(document).ready(function () {

    cargarPluviometros();
    //cargarNombresPluviometros()

});



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

