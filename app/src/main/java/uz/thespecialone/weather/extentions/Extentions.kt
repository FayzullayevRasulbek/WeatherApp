package uz.thespecialone.weather.extentions

import java.text.SimpleDateFormat
import java.util.*

fun getUrl(icon: String) = "http://openweathermap.org/img/w/$icon.png"

fun getDateText(date: Date): String {
    val simpleDateFormat = SimpleDateFormat("E dd MMM HH:mm ", Locale.getDefault())
    return simpleDateFormat.format(date)
}

fun getDateByDtText(dt: Long): String {
    val simpleDateFormat = SimpleDateFormat("E dd MMM HH:mm ", Locale.getDefault())
    return simpleDateFormat.format(Date(dt * 1000L))   //date = dt*1000L
}

fun getTimeByDtText(dt: Long): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDateFormat.format(Date(dt * 1000L))   //date = dt*1000L
}

