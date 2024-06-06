// Call the dataTables jQuery plugin
$(document).ready(function () {
    //cargarNombresPluviometros()
    cargarRegistros();

    //actualizarEmailUsuario();
    //obtenerCantidadPluviometros();


});

function actualizarEmailUsuario() {
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarRegistros() {
    const request = await fetch('api/registroDiarios/mediciones', {
        method: 'GET',
        headers: getHeaders(),
    });
    const registros = await request.json();

    let listadoRegistrosHtml = '';

    for (let registro of registros) {
        let botonEliminar = '<a href="#" onclick="eliminarRegistro(' + registro.id + ')" class="btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i> </a>'
        let registroHtml = '<tr><td>' + registro.id + '</td> <td>' + registro.fecha + '</td> <td>'
            + registro.pluviometroId + '</td> <td>'
            + registro.volumenLluvia + '</td> <td> ' + botonEliminar + ' </td></tr>';
        listadoRegistrosHtml += registroHtml;
    }

    document.querySelector('#registros tbody').outerHTML = listadoRegistrosHtml;
    $('#registros').DataTable();
}

// Funcion para devolver el headers
function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.token
    }

}

// Función para obtener el número de registros de la tabla
// Call the dataTables jQuery plugin

async function cargarNombresPluviometros() {
    try {
        const request = await fetch('api/pluviometros', {
            method: 'GET',
            headers: getHeaders(),
        });
        const pluviometros = await request.json();
        alert(pluviometros);

        // Limpiar el combobox
        const selectPluviometros = document.getElementById('selectPluviometros');
        selectPluviometros.innerHTML = '';

        // Agregar opción "Todos" al combobox
        const optionTodos = document.createElement('option');
        optionTodos.value = 'todos';
        optionTodos.textContent = 'Todos';
        selectPluviometros.appendChild(optionTodos);

        // Agregar opciones de pluviometros al combobox
        pluviometros.forEach(pluviometro => {
            const option = document.createElement('option');
            option.value = pluviometro.id;
            option.textContent = pluviometro.nombre;
            selectPluviometros.appendChild(option);
        });
    } catch (error) {
        console.error('Error al cargar los nombres de los pluviómetros:', error);
    }
}

// Agregar evento de cambio al combobox
selectPluviometros.addEventListener('change', function () {
    const selectedPluviometro = this.value;
    filtrarRegistrosPorPluviometro(selectedPluviometro);
});

async function filtrarRegistrosPorPluviometro(pluviometroId) {
    // Si se seleccionó "Todos", cargar todos los registros
    if (pluviometroId === 'todos') {
        cargarRegistros();
        return;
    }

    const request = await fetch('/api/registroDiarios/mediciones/' + pluviometroId, {
        method: 'GET',
        headers: getHeaders(),
    });
    const registros = await request.json();

    let listadoRegistrosHtml = '';


    for (let registro of registros) {
        let botonEliminar = '<a href="#" onclick="eliminarRegistro(' + registro.id + ')" class="btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i> </a>'
        let registroHtml = '<tr><td>' + registro.id + '</td> <td>' + registro.fecha + '</td> <td>'
            + registro.pluviometroId + '</td> <td>'
            + registro.volumenLluvia + '</td> <td> ' + botonEliminar + ' </td></tr>';
        listadoRegistrosHtml += registroHtml;
    }

    document.querySelector('#registros tbody').outerHTML = listadoRegistrosHtml;
    $('#registros').DataTable();
}

// Agregar evento de cambio al checkbox
document.getElementById('eliminarceros').addEventListener('change', function () {
    const selectedPluviometro = document.getElementById('selectPluviometros').value;
    const eliminarCeros = this.checked;
    filtrarRegistrosPorPluviometrocero(selectedPluviometro, eliminarCeros);
});

async function filtrarRegistrosPorPluviometrocero(pluviometroId, eliminarCeros) {
    const request = await fetch('/api/registroDiarios/mediciones/' + pluviometroId, {
        method: 'GET',
        headers: getHeaders(),
    });
    let registros = await request.json();

    if (eliminarCeros) {
        registros = registros.filter(registro => registro.volumenLluvia !== 0);
    }

    let listadoRegistrosHtml = '';

    for (let registro of registros) {
        let botonEliminar = '<a href="#" onclick="eliminarRegistro(' + registro.id + ')" class="btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i> </a>'
        let registroHtml = '<tr><td>' + registro.id + '</td> <td>' + registro.fecha + '</td> <td>'
            + registro.pluviometroId + '</td> <td>'
            + registro.volumenLluvia + '</td> <td> ' + botonEliminar + ' </td></tr>';
        listadoRegistrosHtml += registroHtml;
    }

    document.querySelector('#registros tbody').outerHTML = listadoRegistrosHtml;
    $('#registros').DataTable();
}
