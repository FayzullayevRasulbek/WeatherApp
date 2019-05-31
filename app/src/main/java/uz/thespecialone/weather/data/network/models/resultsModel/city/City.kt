package uz.thespecialone.weather.data.network.models.resultsModel.city

import uz.thespecialone.weather.data.network.models.resultsModel.Coord
import java.util.*

data class City(
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val timeZone: TimeZone

)

