package uz.thespecialone.weather.data.network.models.responses

import uz.thespecialone.weather.data.network.models.resultsModel.city.City
import uz.thespecialone.weather.data.network.models.resultsModel.city.WeatherData

data class CityWeatherResponse(
    val cod: String,
    val message: Float,
    val cnt: Int,
    val list: List<WeatherData>,
    val city: City
)