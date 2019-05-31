package uz.thespecialone.weather.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import uz.thespecialone.weather.*
import uz.thespecialone.weather.data.network.models.responses.CurrentWeatherResponse
import uz.thespecialone.weather.data.network.models.resultsModel.group.GroupWeatherData
import uz.thespecialone.weather.extentions.getStatusCheck
import uz.thespecialone.weather.extentions.hasLocationPermission
import uz.thespecialone.weather.mvp.main.MainPresenterImpl
import uz.thespecialone.weather.mvp.main.MainView


class MainActivity : AppCompatActivity(), MainView {
    private val router = App.INSTANCE!!.getRouter()
    private val navigatorHolder = App.INSTANCE!!.provideNavigatorHolder()
    private val navigator: Navigator = SupportAppNavigator(this, R.id.main_container)
    private lateinit var presenter: MainPresenterImpl
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var compositeDisposable: CompositeDisposable
    lateinit var listener: LocationListener
    lateinit var locationManager: LocationManager

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
        if (hasLocationPermission() && presenter.getLocation(listener, locationManager) != null)
            presenter.loadLocationData(presenter.getLocation(listener, locationManager)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        compositeDisposable = CompositeDisposable()
        presenter = MainPresenterImpl(router, compositeDisposable, this)
        initLocationListener()
        initRV()
        presenter.loadData()
    }

    private fun initLocationListener() {
        locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
        listener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                if (location != null) {
                    Log.d("MAIN_TAG", "onLocationChanged")
                    presenter.loadData()
                    presenter.loadLocationData(location)
                    locationManager.removeUpdates(listener)
                } else Log.d("MAIN_TAG", "onLocationChanged location = null")
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String?) {}
            override fun onProviderDisabled(provider: String?) {}
        }
    }

    private fun initRV() {
        rv.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter({ presenter.onItemClick(it) }, { refresh() }, true)
        rv.adapter = adapter
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun refresh() {
        if (hasLocationPermission()) presenter.refresh(listener, locationManager)
        presenter.loadData()
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
    }

    override fun setData(data: List<GroupWeatherData>) {
        adapter.setData(data)
    }

    override fun message(message: String) {
        adapter.isEmpty = false
        adapter.notifyDataSetChanged()
    }

    override fun setLocationData(data: CurrentWeatherResponse) {
        adapter.setLocationData(data)
    }

    override fun locationMessage(message: String) {
        Toast.makeText(this, "Can't find location", Toast.LENGTH_SHORT).show()
    }

    override fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }


    override fun statusCheck() {
        getStatusCheck()
    }

    override fun checkSelfPermission() =
        (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)


    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.clearAndDisposeDisposable()
        super.onDestroy()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
