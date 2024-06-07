async function cargarDatosYCrearGrafico() {
  try {
    const response = await fetch('api/registroDiarios/volumenPorMes', {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.token
      }
    });
    const dataPorMes = await response.json();
    crearGrafico(dataPorMes);
  } catch (error) {
    console.error('Error al cargar los datos del volumen de lluvia por mes:', error);
  }
}

function crearGrafico(dataPorMes) {
  var ctx = document.getElementById("myBarChart").getContext('2d');
  var myBarChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"],
      datasets: [{
        label: 'Volumen de lluvia',
        data: dataPorMes,
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      }
    }
  });
}

window.onload = function() {
  alert('La página bar chart se ha cargado correctamente.'); // Verifica si la página se ha cargado correctamente
  cargarDatosYCrearGrafico();
};

