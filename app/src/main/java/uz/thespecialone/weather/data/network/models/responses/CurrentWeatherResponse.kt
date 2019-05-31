package uz.thespecialone.weather.data.network.models.responses

import uz.thespecialone.weather.data.network.models.resultsModel.*
import uz.thespecialone.weather.data.network.models.resultsModel.city.MainWeatherData

data class CurrentWeatherResponse(
    val coord: Coord,
    val weather: List<WeatherDescription>,
    val base: String,
    val main: MainWeatherData,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Int
)