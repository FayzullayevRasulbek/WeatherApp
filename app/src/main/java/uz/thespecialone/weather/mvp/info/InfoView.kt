package uz.thespecialone.weather.mvp.info

import io.reactivex.disposables.Disposable
import uz.thespecialone.weather.data.network.models.responses.CityWeatherResponse
import uz.thespecialone.weather.data.network.models.responses.CurrentWeatherResponse

interface InfoView {
    fun addDisposable(d: Disposable)

    fun setData(data: CityWeatherResponse)

    fun message(message: String)

    fun setCurrentData(data: CurrentWeatherResponse)
}