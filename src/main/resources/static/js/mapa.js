let map;

function initMap() {
    // Coordenadas aproximadas del centro de la Región de Murcia
    const murciaCoords = { lat: 37.983444, lng: -1.129889 };

    // Inicializar el mapa centrado en la Región de Murcia
    map = new google.maps.Map(document.getElementById("map"), {
        center: murciaCoords,
        zoom: 9,  // Ajustar el nivel de zoom según sea necesario
    });

    // Cargar los pluviómetros en el mapa y en la lista después de inicializar el mapa
    cargarPluviometros();
}


function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.token
    };
}

async function cargarPluviometros() {
    const request = await fetch('api/pluviometros', {
        method: 'GET',
        headers: getHeaders(),
    });
    const pluviometros = await request.json();

    let listadoPluviometrosHtml = '';

    for (let pluviometro of pluviometros) {
        // Agregar marcador al mapa
        new google.maps.Marker({
            position: { lat: parseFloat(pluviometro.latitud), lng: parseFloat(pluviometro.longitud) },
            map,
            title: pluviometro.nombre,
        });

        // Crear fila para la tabla
        let botonEliminar = '<a href="#" onclick="eliminarPluviometro(' + pluviometro.id + ')" class="btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i> </a>';
        let pluviometroHtml = '<tr><td>' + pluviometro.id + '</td> <td>' + pluviometro.nombre + '</td> <td>'
            + pluviometro.latitud + '</td> <td>'
            + pluviometro.longitud + '</td> <td> ' ;
        listadoPluviometrosHtml += pluviometroHtml;
    }

    // Insertar filas en la tabla
    document.querySelector('#pluviometros tbody').innerHTML = listadoPluviometrosHtml;

    // Inicializar DataTables
    $('#pluviometros').DataTable();
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

