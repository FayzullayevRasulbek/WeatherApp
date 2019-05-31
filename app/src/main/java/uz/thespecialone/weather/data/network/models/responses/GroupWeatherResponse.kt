package uz.thespecialone.weather.data.network.models.responses

import uz.thespecialone.weather.data.network.models.resultsModel.group.GroupWeatherData

data class GroupWeatherResponse(val cnt: Int, val list: List<GroupWeatherData>)