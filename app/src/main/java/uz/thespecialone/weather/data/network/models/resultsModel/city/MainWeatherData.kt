package uz.thespecialone.weather.data.network.models.resultsModel.city

import com.google.gson.annotations.SerializedName

data class MainWeatherData(
    val temp: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float,
    val pressure: Float,
    @SerializedName("sea_level")
    val seaLevel: Float,
    @SerializedName("grnd_level")
    val grndLevel: Float,
    val humidity: Float,
    @SerializedName("temp_kf")
    val tempKf: Float

)