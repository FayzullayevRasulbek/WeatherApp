package uz.thespecialone.weather.data.network.models.resultsModel.group

data class Sys(
    val country: String,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long
)