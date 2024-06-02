// Inicializa el plugin de jQuery dataTables cuando el documento está listo
$(document).ready(function() {
    // on Ready
});

// Función asíncrona para registrar un Pluviometro
async function registrarPluviometro() {
    // Recoge los valores de los campos de entrada del formulario y los almacena en un objeto
    let datos = {
        nombre: document.getElementById('txtNombre').value.trim(),
        latitud: document.getElementById('txtLatitud').value.trim(),
        longitud: document.getElementById('txtLongitud').value.trim(),
    };

    // Validar que ninguno de los campos está vacío
    if (!datos.nombre || !datos.latitud || !datos.longitud) {
        alert("Por favor, complete todos los campos.");
        return;
    }

    // Validar que latitud y longitud son números decimales válidos
    if (!esDecimal(datos.latitud) || !esDecimal(datos.longitud)) {
        alert("Por favor, ingrese valores numéricos válidos para la latitud y la longitud.");
        return;
    }

    const request = await fetch('api/pluviometros', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    // Una vez que la solicitud se ha completado, muestra una alerta al usuario indicando que el registro se ha realizado correctamente
    alert("Pluviometro registrado correctamente");

    // Redirige al usuario a la página de inicio de sesión
    window.location.href = '/pluviometro';
}

// Función para validar si un valor puede ser convertido a un número decimal
function esDecimal(valor) {
    return !isNaN(valor) && !isNaN(parseFloat(valor));
}



