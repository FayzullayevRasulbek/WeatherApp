package uz.thespecialone.weather.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_city_layout.view.*
import kotlinx.android.synthetic.main.item_empty_city_layout.view.*
import uz.thespecialone.weather.R
import uz.thespecialone.weather.data.network.models.responses.CurrentWeatherResponse
import uz.thespecialone.weather.data.network.models.resultsModel.group.GroupWeatherData
import uz.thespecialone.weather.extentions.getDateByDtText
import uz.thespecialone.weather.extentions.getUrl
import kotlin.collections.ArrayList


class RecyclerViewAdapter(
    val clickListener: (Int) -> Unit,
    val refreshListener: (Int) -> Unit,
    var isEmpty: Boolean = false
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data: ArrayList<GroupWeatherData> = arrayListOf()
    private var locationData: CurrentWeatherResponse? = null
    private val emptyView = 99
    var count = if (locationData != null) data.size + 1 else data.size

    private fun notifyCount() {
        count = if (locationData != null) data.size + 1 else data.size
        notifyDataSetChanged()
    }

    fun setData(data: List<GroupWeatherData>?) {
        if (data != null) {
            this.data.clear()
            this.data.addAll(data)
            notifyCount()
        }
    }

    fun setLocationData(locationData: CurrentWeatherResponse?) {
        if (locationData != null) {
            this.locationData = locationData
            notifyCount()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == emptyView) {
            RecyclerEmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_empty_city_layout,
                    parent,
                    false
                )
            )
        } else {
            RecyclerViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_city_layout,
                    parent,
                    false
                )
            )

        }


    override fun getItemCount() = when {
        isEmpty -> count
        count > 0 -> count
        else -> 1
    }


    override fun getItemViewType(position: Int) = when (count) {
        0 -> emptyView
        else -> 1
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when {
            getItemViewType(position) == emptyView -> (holder as RecyclerEmptyViewHolder).bind()
            count == data.size -> (holder as RecyclerViewHolder).bind(data[position])
            position == 0 -> (holder as RecyclerViewHolder).locationBind(locationData!!)
            else -> (holder as RecyclerViewHolder).bind(data[position - 1])
        }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { clickListener(adapterPosition + data.size - count) }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: GroupWeatherData) = with(itemView) {
            Picasso.get().load(getUrl(data.weather[0].icon)).into(img)
            city_tv.text = data.name
            country_tv.text = data.sys.country
            date_tv.text = getDateByDtText(data.dt)
            temp_tv.text = "${data.main.temp.toInt()}°"
            max_min_temp_tv.text = "${data.main.tempMin.toInt()}°/${data.main.tempMax.toInt()}°"
            city_tv.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, 0, 0
            )
        }

        @SuppressLint("SetTextI18n")
        fun locationBind(data: CurrentWeatherResponse) = with(itemView) {
            Picasso.get().load(getUrl(data.weather[0].icon)).into(img)
            city_tv.text = data.name
            city_tv.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_location_on_black_24dp, 0, 0, 0
            )
            country_tv.text = "Current Location"
            date_tv.text = getDateByDtText(data.dt)
            temp_tv.text = "${data.main.temp.toInt()}°"
            max_min_temp_tv.text = "${data.main.tempMin.toInt()}°/${data.main.tempMax.toInt()}°"
        }
    }

    inner class RecyclerEmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.refresh.setOnClickListener { refreshListener(adapterPosition) }
        }

        fun bind() = with(itemView) {
            // do something
        }
    }
}