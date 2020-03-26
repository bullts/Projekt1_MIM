import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pogodynkamim.data.CurrentWeatherResponse
import com.example.pogodynkamim.data.ICurrentWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CurrentWeather(val city: String) {


    val list : LiveData<CurrentWeatherResponse> = getCurrentWeater()

    fun getCurrentWeater() : MutableLiveData<CurrentWeatherResponse>{
        var list : MutableLiveData<CurrentWeatherResponse> = MutableLiveData<CurrentWeatherResponse>()

        val BASEURL: String = "https://api.openweathermap.org/data/2.5/"
        var CITY: String = city
        val APIKEY: String = "c4ffc65c1eda93d335ccc1b45fbab2cc"

        val retrofit = Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(ICurrentWeather::class.java)
        val call = service.getWeatherResponse(CITY,APIKEY)
        Log.i("API REQUEST URL: ",call.request().url().toString())// tu dobrze

        call.enqueue(object : Callback<CurrentWeatherResponse>{

            override fun onResponse(
                call: Call<CurrentWeatherResponse>?,
                response: Response<CurrentWeatherResponse>?
            ) {
                list.value = response!!.body()
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>?, t: Throwable?) {
                Log.i("nie dziala dlaczego: ",t!!.message.toString())
            }
        })


        return list
    }
}