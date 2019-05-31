package uz.thespecialone.weather.extentions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val PREFS_FILE_NAME = "preference_permission"
const val PREFS_FIRST_TIME_KEY = "is_app_launched_first_time"

fun Activity.hasLocationPermission(): Boolean {
    if (ContextCompat.checkSelfPermission(
            this.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("To view the weather forecast for your current location, Location will be turned on and the Locating method will be set for to GPS,Wi-FI and mobile networks.")
                .setCancelable(false)
                .setPositiveButton("TURN ON") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        9999
                    )
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.show()

            return false
        } else {
            val sharedPreferences = this.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
            if (sharedPreferences.getBoolean(PREFS_FIRST_TIME_KEY, true)) {
                val editor = sharedPreferences.edit()
                editor.putBoolean(PREFS_FIRST_TIME_KEY, false)
                editor.apply()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    9999
                )
            }
            return false
        }
    }
    return true
}

fun Activity.getStatusCheck() {
    val manager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        this.buildAlertMessageNoGps()
    }
}

private fun Activity.buildAlertMessageNoGps() {
    val builder = AlertDialog.Builder(this)
    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
        .setCancelable(false)
        .setPositiveButton("Yes") { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
        .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
    val alert = builder.create()
    alert.show()
}