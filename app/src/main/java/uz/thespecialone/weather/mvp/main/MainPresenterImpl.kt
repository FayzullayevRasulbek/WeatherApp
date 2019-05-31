package uz.thespecialone.weather.mvp.main

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import uz.thespecialone.weather.Screens
import uz.thespecialone.weather.data.network.ApiClient
import uz.thespecialone.weather.data.network.api.WeatherApi
import uz.thespecialone.weather.data.network.models.responses.CurrentWeatherResponse
import uz.thespecialone.weather.data.network.models.responses.GroupWeatherResponse
import uz.thespecialone.weather.extentions.subscribeEx

class MainPresenterImpl(
    private val router: Router,
    private val compositeDisposable: CompositeDisposable,
    private val mainView: MainView
) : MainPresenter {
    private lateinit var groupWeatherResponse: GroupWeatherResponse
    private lateinit var currentWeatherResponse: CurrentWeatherResponse

    override fun loadLocationData(location: Location) {
        ApiClient.retrofit.create(WeatherApi::class.java)
            .getCurrentWeatherDataByLocation(location.latitude, location.longitude)
            .subscribeEx({ getIt(it) }, { getLocationThrowable(it) })
    }

    override fun loadData() {
        mainView.addDisposable(
            ApiClient.retrofit.create(WeatherApi::class.java)
                .getWeatherByGroupIds()
                .subscribeEx(
                    { getIt(it) }, { getThrowable(it) }
                )
        )
    }

    private fun getIt(it: GroupWeatherResponse?) {
        if (it != null) {
            groupWeatherResponse = it
            mainView.setData(groupWeatherResponse.list)
        }
    }

    private fun getIt(it: CurrentWeatherResponse?) {
        if (it != null) {
            currentWeatherResponse = it
            mainView.setLocationData(currentWeatherResponse)
        }
    }

    private fun getThrowable(throwable: Throwable) {
        mainView.message("throwable=$throwable")
    }

    private fun getLocationThrowable(it: Throwable) {
        mainView.locationMessage("throwable=$it")
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun clearAndDisposeDisposable() {
        compositeDisposable.dispose()
    }


    override fun onItemClick(pos: Int) {
        if (pos != -1) router.navigateTo(Screens.InfoScreen(groupWeatherResponse.list[pos].id))
        else router.navigateTo(Screens.InfoScreen(currentWeatherResponse.id))   // Current Location Weather
    }

    override fun refresh(listener: LocationListener, locationManager: LocationManager) {
        if (getLocation(listener, locationManager) != null)
            loadLocationData(getLocation(listener, locationManager)!!)
    }

    override fun getLocation(
        listener: LocationListener,
        locationManager: LocationManager
    ): Location? {
        mainView.statusCheck()
        val isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!isGPSEnable && !isNetworkEnable) return null
        else when {
            isNetworkEnable -> {
                if (mainView.checkSelfPermission()) return null
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000,
                    0f,
                    listener
                )
                return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            isGPSEnable -> {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    0f,
                    listener
                )
                return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            else -> return null
        }
    }

    override fun onBackPressed() {
        router.exit()
    }
}