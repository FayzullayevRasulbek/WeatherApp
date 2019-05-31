package uz.thespecialone.weather.data.network.models.resultsModel

import com.google.gson.annotations.SerializedName

data class MainWeatherData(
    val temp: Float,
    val pressure: Float,
    val humidity: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float
)