package uz.thespecialone.weather.data.network.models.resultsModel.city

import com.google.gson.annotations.SerializedName
import uz.thespecialone.weather.data.network.models.resultsModel.Clouds
import uz.thespecialone.weather.data.network.models.resultsModel.WeatherDescription
import uz.thespecialone.weather.data.network.models.resultsModel.Wind

data class WeatherData(
    val dt: Long,
    val main: MainWeatherData,
    val weather: List<WeatherDescription>,
    val clouds: Clouds,
    val wind: Wind,
    val sys: Sys,
    @SerializedName("dt_txt")
    val dtTxt: String

)



