package uz.thespecialone.weather.mvp.main

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

interface MainPresenter {
    fun loadData()

    fun loadLocationData(location: Location)

    fun onItemClick(pos: Int)

    fun onBackPressed()

    fun clearDisposable()

    fun clearAndDisposeDisposable()

    fun refresh(listener: LocationListener, locationManager: LocationManager)

    fun getLocation(listener: LocationListener, locationManager: LocationManager): Location?

}