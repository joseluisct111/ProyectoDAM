// Definir la función para actualizar el correo electrónico del usuario
function actualizarEmailUsuario() {
    // Verificar si el valor está disponible en localStorage y es válido
    if (localStorage && localStorage.email) {
        // Seleccionar el elemento HTML para actualizar
        var elementoEmailUsuario = document.getElementById('txt-email-usuario');
        // Verificar si se ha encontrado el elemento
        if (elementoEmailUsuario) {
            // Asignar el contenido del localStorage al elemento
            elementoEmailUsuario.textContent = localStorage.email;
        } else {
            console.error('Elemento con ID "txt-email-usuario" no encontrado.');
        }
    } else {
        console.error('Valor de correo electrónico no encontrado en localStorage.');
    }
}

// Llamar a la función al cargar la página
window.onload = function() {
    actualizarEmailUsuario();

};
// Definir la función para cerrar sesión y borrar los datos del localStorage
function cerrarSesion() {
    // Eliminar los datos guardados del usuario en localStorage
    localStorage.removeItem('email');
    localStorage.removeItem('token');
    // Redirigir a la página de inicio de sesión u otra página relevante
    window.location.href = 'login.html';
}
let chart;

async function cargarDatos() {
    const pluviometros = await obtenerPluviometros();
    const years = await obtenerYears();

    const selectPluviometro = document.getElementById('selectPluviometro');
    const selectYear = document.getElementById('selectYear');
    pluviometros.forEach(pluviometro => {
        const option = document.createElement('option');
        option.value = pluviometro.id;
        option.textContent = pluviometro.nombre;
        selectPluviometro.appendChild(option);
    });
    years.forEach(year => {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        selectYear.appendChild(option);
    });

    selectPluviometro.addEventListener('change', actualizarGrafica);
    selectYear.addEventListener('change', actualizarGrafica);

    actualizarGrafica();
}

async function obtenerPluviometros() {
    const response = await fetch('api/pluviometros');
    return response.json();
}

async function obtenerYears() {
    const response = await fetch('api/years');
    return response.json();
}

async function obtenerLluviaPorMes(pluviometro, year) {
    const response = await fetch(`/api/lluviaPorMes?pluviometro=${pluviometro}&year=${year}`);
    return response.json();
}

async function actualizarGrafica() {
    const selectedPluviometro = document.getElementById('selectPluviometro').value;
    const selectedYear = document.getElementById('selectYear').value;

    const lluviaPorMes = await obtenerLluviaPorMes(selectedPluviometro, selectedYear);

    const ctx = document.getElementById('myChart').getContext('2d');
    if (chart) {
        chart.destroy();
    }
    chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            datasets: [{
                label: 'Lluvia por mes',
                data: lluviaPorMes,
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        }
    });
}

// Llama a la función cargarDatos cuando el documento esté listo
$(document).ready(function() {
    cargarDatos();
});

