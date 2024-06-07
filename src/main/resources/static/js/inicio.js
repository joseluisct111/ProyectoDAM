
function loadIframe(municipio) {
    var iframe = document.getElementById('iframe_aemet_id33044');
    iframe.src = 'https://www.aemet.es/es/eltiempo/prediccion/municipios/mostrarwidget/' + municipio;
}

async function cargarNumeroPluviometros() {
    try {
        const response = await fetch('api/pluviometros/count', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': sessionStorage.token
            }
        });
        const data = await response.json();
        alert('Número de pluviómetros: ' + data);
        document.getElementById('pluviometros-count').textContent = data;
    } catch (error) {
        console.error('Error al cargar el número de pluviómetros:', error);
    }
}


window.onload = function () {
    alert('La página se ha cargado correctamente.'); // Verifica si la página se ha cargado correctamente
    actualizarEmailUsuario();
    loadIframe('murcia-id30030');
    cargarNumeroPluviometros();
}
