package uz.thespecialone.weather.mvp.info

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import uz.thespecialone.weather.data.network.ApiClient
import uz.thespecialone.weather.data.network.api.WeatherApi
import uz.thespecialone.weather.data.network.models.responses.CityWeatherResponse
import uz.thespecialone.weather.data.network.models.responses.CurrentWeatherResponse
import uz.thespecialone.weather.extentions.subscribeEx

class InfoPresenterImpl(
    private val compositeDisposable: CompositeDisposable,
    private val infoView: InfoView
) : InfoPresenter {
    private lateinit var cityWeatherResponse: CityWeatherResponse
    private lateinit var currentWeatherResponse: CurrentWeatherResponse
    override fun loadData(id: Long) {
        infoView.addDisposable(
            ApiClient.retrofit.create(WeatherApi::class.java)
                .getWeatherByCityId("$id")
                .subscribeEx({ getIt(it) }, { Log.d("TAG_RX", "it=$it") })
        )

        infoView.addDisposable(
            ApiClient.retrofit.create(WeatherApi::class.java)
                .getCurrentWeatherData("$id")
                .subscribeEx({ getIt(it) }, { message(it) })
        )
    }

    private fun getIt(it: CityWeatherResponse?) {
        if (it != null) {
            cityWeatherResponse = it
            Log.d("TAG_RX", "get it= $it")
            infoView.setData(it)
        }
    }

    private fun getIt(it: CurrentWeatherResponse?) {
        if (it != null) {
            currentWeatherResponse = it
            Log.d("TAG_RX", "get it= $it")
            infoView.setCurrentData(it)
        }
    }

    private fun message(it: Throwable) {
        infoView.message("it = $it")
    }

    override fun clearAndDisposeDisposable() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}