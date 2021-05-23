package uz.thespecialone.weather.ui.info

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

import uz.thespecialone.weather.data.network.models.responses.CityWeatherResponse
import uz.thespecialone.weather.data.network.models.responses.CurrentWeatherResponse
import uz.thespecialone.weather.data.network.models.resultsModel.city.WeatherData
import uz.thespecialone.weather.mvp.info.InfoPresenterImpl
import uz.thespecialone.weather.mvp.info.InfoView
import java.util.*
import android.annotation.SuppressLint
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_info.*
import uz.thespecialone.weather.R
import uz.thespecialone.weather.extentions.getDateText
import uz.thespecialone.weather.extentions.getUrl

private const val ID = "ID"

class InfoFragment : Fragment(), InfoView {
    private lateinit var presenter: InfoPresenterImpl
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var adapter: WeatherRecyclerViewAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        compositeDisposable = CompositeDisposable()
        presenter = InfoPresenterImpl(compositeDisposable, this)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        initChart()
        presenter.loadData(arguments!![ID] as Long)
        refresh.setOnClickListener {
            presenter.loadData(arguments!![ID] as Long)
            Toast.makeText(activity, "Refresh", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRV() {
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = WeatherRecyclerViewAdapter()
        rv.adapter = adapter
    }

    private fun initChart() {
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setTouchEnabled(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(false)
        chart.setNoDataText("")
    }

    @SuppressLint("SetTextI18n")
    override fun setCurrentData(data: CurrentWeatherResponse) {
        view_group_refresh.visibility = View.GONE
        line.visibility = View.VISIBLE
        hourly_tv.text = "HOURLY"
        name_tv.text = data.name
        date_tv.text = getDateText(Calendar.getInstance().time)
        val descriptionText = data.weather[0].description
        description_tv.text =
            descriptionText.substring(0, 1).toUpperCase() + descriptionText.substring(1)
        Picasso.get().load(getUrl(data.weather[0].icon)).into(current_img)
        current_temp_tv.text = "${data.main.temp.toInt()}"
        temp_symbol_tv.text = "°"
        temp_max_min_tv.text =
            "${data.main.tempMax.toInt()}°/${data.main.tempMin.toInt()}°  |  Humidity ${data.main.humidity.toInt()}%"
    }

    override fun setData(data: CityWeatherResponse) {
        adapter.setData(data.list)
        loadChart(data.list)
    }

    override fun message(message: String) {
        view_group_refresh.visibility = View.VISIBLE
    }

    override fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

    private fun loadChart(list: List<WeatherData>) {
        if (chart.data != null)
            chart.data.clearValues()
        chart.data = getLineData(list)
        chart.invalidate()

        // get the legend (only possible after setting data)
        val l = chart.legend
        l.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.axisLeft.spaceTop = 40f
        chart.axisLeft.spaceBottom = 40f
        chart.axisRight.isEnabled = false
        chart.xAxis.isEnabled = false
        chart.animateY(500)
    }

    private fun getLineData(list: List<WeatherData>): LineData {
        val values = arrayListOf<Entry>()

        for (i in 0 until list.size)
            values.add(Entry(i.toFloat(), list[i].main.temp))

        val set1 = LineDataSet(values, "Weather")
        set1.lineWidth = 0.25f
        set1.circleRadius = 2.5f
        set1.circleHoleRadius = 1.0f
        set1.color = Color.WHITE
        set1.valueTextSize = 12f
        set1.valueTextColor = Color.WHITE
        set1.setCircleColor(Color.WHITE)
        set1.highLightColor = Color.WHITE
        set1.setDrawValues(true)
        set1.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}°"
            }
        }

        return LineData(set1)
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.show()

    }

    override fun onDetach() {
        presenter.clearAndDisposeDisposable()
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun getNewInstance(id: Long): InfoFragment = InfoFragment().also {
            it.arguments = Bundle().also { it1 ->
                it1.putLong(ID, id)
            }
        }
    }
}
