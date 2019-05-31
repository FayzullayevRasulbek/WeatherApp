package uz.thespecialone.weather.data.network.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import uz.thespecialone.weather.data.Data
import uz.thespecialone.weather.data.network.models.responses.CityWeatherResponse
import uz.thespecialone.weather.data.network.models.responses.CurrentWeatherResponse
import uz.thespecialone.weather.data.network.models.responses.GroupWeatherResponse

public interface WeatherApi {
    @GET("data/2.5/forecast")
    fun getWeatherByCityId(
        @Query("id") id: String,
        @Query("cnt") cnt: String = "6",  //hourly
        @Query("appid") appid: String = "65f04eb873060a67114dfbfe8dd4e531",
        @Query("units") units: String = "metric"
    ): Single<CityWeatherResponse>

    @GET("data/2.5/group")
    fun getWeatherByGroupIds(
        @Query("id") id: String = Data.getGroupsId(),
        @Query("appid") appid: String = "65f04eb873060a67114dfbfe8dd4e531",
        @Query("units") units: String = "metric"
    ): Single<GroupWeatherResponse>

    @GET("data/2.5/weather")
    fun getCurrentWeatherData(
        @Query("id") id: String,
        @Query("appid") appid: String = "65f04eb873060a67114dfbfe8dd4e531",
        @Query("units") units: String = "metric"
    ): Single<CurrentWeatherResponse>

    @GET("data/2.5/weather")
    fun getCurrentWeatherDataByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = "65f04eb873060a67114dfbfe8dd4e531",
        @Query("units") units: String = "metric"
    ): Single<CurrentWeatherResponse>


}