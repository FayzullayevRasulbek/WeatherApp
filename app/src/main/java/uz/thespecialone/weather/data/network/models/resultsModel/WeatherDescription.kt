package uz.thespecialone.weather.data.network.models.resultsModel

data class WeatherDescription(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)