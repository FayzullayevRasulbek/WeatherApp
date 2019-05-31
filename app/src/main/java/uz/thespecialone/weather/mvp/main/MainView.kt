package uz.thespecialone.weather.mvp.main

import io.reactivex.disposables.Disposable
import uz.thespecialone.weather.data.network.models.responses.CurrentWeatherResponse
import uz.thespecialone.weather.data.network.models.resultsModel.group.GroupWeatherData

interface MainView {

    fun setData(data: List<GroupWeatherData>)

    fun setLocationData(data: CurrentWeatherResponse)

    fun addDisposable(d: Disposable)

    fun message(message: String)

    fun locationMessage(message: String)

    fun statusCheck()

    fun checkSelfPermission(): Boolean

}