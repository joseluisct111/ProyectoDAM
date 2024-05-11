$(document).ready(function(){
    var apiKey = '7f4d5558cbe31c67719f2d35c1f50c8f'; // Reemplaza 'TU_CLAVE_DE_API' con tu clave de API
    var city = 'London'; // Cambia 'London' por la ciudad de la que deseas obtener el clima

    var apiUrl = 'https://api.openweathermap.org/data/2.5/weather?q=' + city + '&appid=' + apiKey;

    $.getJSON(apiUrl, function(data){
        var weatherInfo = '<h2>' + data.name + ', ' + data.sys.country + '</h2>';
        weatherInfo += '<p>' + data.weather[0].description + '</p>';
        weatherInfo += '<p>Temperature: ' + data.main.temp + '°C</p>';
        weatherInfo += '<p>Humidity: ' + data.main.humidity + '%</p>';
        weatherInfo += '<p>Wind Speed: ' + data.wind.speed + ' m/s</p>';

        $('#weatherData').html(weatherInfo);
    });
});

