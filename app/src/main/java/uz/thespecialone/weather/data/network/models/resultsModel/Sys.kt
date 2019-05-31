package uz.thespecialone.weather.data.network.models.resultsModel

data class Sys(
    val type: Int,
    val id: Long,
    val message: Float,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)