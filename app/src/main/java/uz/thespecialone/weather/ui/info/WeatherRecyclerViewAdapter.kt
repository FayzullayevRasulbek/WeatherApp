package uz.thespecialone.weather.ui.info

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_city_layout.view.img
import kotlinx.android.synthetic.main.item_weather_layout.view.*
import uz.thespecialone.weather.R
import uz.thespecialone.weather.data.network.models.resultsModel.city.WeatherData
import uz.thespecialone.weather.extentions.getTimeByDtText
import uz.thespecialone.weather.extentions.getUrl
import java.util.*
import kotlin.collections.ArrayList


class WeatherRecyclerViewAdapter :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.RecyclerViewHolder>() {
    private val data: ArrayList<WeatherData> = arrayListOf()

    fun setData(data: List<WeatherData>?) {
        if (data != null) {
            this.data.clear()
            this.data.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
        RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_weather_layout,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(data[position])

    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(weatherData: WeatherData) = with(itemView) {
            val time: String = getTimeByDtText(weatherData.dt)
            Log.d("TIMERX", Date(weatherData.dt * 1000L).toString())
            time_tv.text = time
            Picasso.get().load(getUrl(weatherData.weather[0].icon)).error(R.mipmap.ic_launcher)
                .into(img)
            humidity_tv.text = "${weatherData.main.humidity.toInt()}%"
        }
    }
}