package uz.thespecialone.weather.data.network.models.resultsModel.group

import com.google.gson.annotations.SerializedName

data class MainGroupWeatherData(
    val temp: Float,
    val pressure: Float,
    val humidity: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float
)