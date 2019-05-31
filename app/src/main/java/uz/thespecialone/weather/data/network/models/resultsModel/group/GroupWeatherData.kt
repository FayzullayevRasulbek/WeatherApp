package uz.thespecialone.weather.data.network.models.resultsModel.group

import uz.thespecialone.weather.data.network.models.resultsModel.Clouds
import uz.thespecialone.weather.data.network.models.resultsModel.Coord
import uz.thespecialone.weather.data.network.models.resultsModel.WeatherDescription
import uz.thespecialone.weather.data.network.models.resultsModel.Wind

data class GroupWeatherData(
    val coord: Coord,
    val sys: Sys,
    val weather: List<WeatherDescription>,
    val main: MainGroupWeatherData,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val id: Long,
    val name: String
)