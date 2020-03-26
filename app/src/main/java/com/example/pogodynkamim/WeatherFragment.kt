package com.example.pogodynkamim

import CurrentWeather
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mancj.materialsearchbar.MaterialSearchBar
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getting city name to find forecast
        var cityForecast = view.findViewById<TextInputEditText>(R.id.textFieldCityName).text.toString()
        if (arguments?.getString("city") != null){
            cityForecast = arguments?.getString("city").toString()
        }
        view.findViewById<TextInputEditText>(R.id.textFieldCityName).setText(cityForecast)

        view.findViewById<TextInputLayout>(R.id.textField).setEndIconOnClickListener { SetCity(view.findViewById<TextInputEditText>(R.id.textFieldCityName).text.toString()) }


        // tworzenie view modelu
        val viewModel = ViewModelProviders.of(this, WeatherViewModelFactory(activity!!.baseContext,cityForecast))
            .get(WeatherViewModel::class.java)




        viewModel.allWeather.observe(this, Observer { list ->
            if(list != null){


                //getting temperature in degree
                view.findViewById<TextView>(R.id.textViewTemp).setText((list.main.temp - 273.15).toInt().toString() +  0x00B0.toChar())  //convert to celsius degrees

                //air pressure
                view.findViewById<TextView>(R.id.textViewBar).text = (list.main.pressure.toString() + " hPa")

                //humidity
                view.findViewById<TextView>(R.id.textViewHumidity).text = (list.main.humidity.toString() + " %")

                //sunrise and sunset time in hh:mm
                val f = SimpleDateFormat("HH:mm")
                //sunrise
                val sunrise = Date(list.sys.sunrise.toLong()*1000) //+3600*1000 to get UTC+1 time
                view.findViewById<TextView>(R.id.textViewSunriseTime).text = f.format(sunrise).toString()
                //sunset
                val sunset = Date(list.sys.sunset.toLong()*1000) //+3600*1000 to get UTC+1 time
                view.findViewById<TextView>(R.id.textViewSunsetTime).text = f.format(sunset).toString()

                //date and time
                val f2 = SimpleDateFormat("dd MMMM yyyy' 'HH:mm", Locale("EN"))
                val dateTimeNow = Date(list.dt.toLong()*1000)
                view.findViewById<TextView>(R.id.textViewDateTime).text = f2.format(dateTimeNow).toString()

                //weather icon
                val iconImg = view.findViewById<ImageView>(R.id.imageViewWeatherIcon)
                when(list.weather.first().id)
                {
                    in 200..299 -> iconImg.setImageResource(R.drawable.lightning)
                    in 300..399 -> iconImg.setImageResource(R.drawable.partlyrainy)
                    in 500..599 -> iconImg.setImageResource(R.drawable.rainy)
                    in 600..699 -> iconImg.setImageResource(R.drawable.snow)
                    in 700..799 -> iconImg.setImageResource(R.drawable.fog)
                    800 -> iconImg.setImageResource(R.drawable.sunny)
                    801 -> iconImg.setImageResource(R.drawable.partlycloudy)
                    802 -> iconImg.setImageResource(R.drawable.cloudy)
                    803 -> iconImg.setImageResource(R.drawable.cloudy)
                    804 -> iconImg.setImageResource(R.drawable.cloudyalert)
                    else -> iconImg.setImageResource(R.drawable.rainy)
                }

                //little description
                view.findViewById<TextView>(R.id.textViewDescriptionShort).text = list.weather.first().main

                // description
                view.findViewById<TextView>(R.id.textViewDescription).text = list.weather.first().description


                //Log.i("działa w WF ",lists.sys.sunrise.toString()) //o kurde jednak działa
            }
            else{
                Log.i("nie działa w WF","nie dziala")
            }
        })



    }

    //function for refreshing fragment when new data is called
    fun SetCity(city: String) {
        val weatherFragment = WeatherFragment()
        val bundle = Bundle()
        bundle.putString("city", city.toString())
        weatherFragment.arguments = bundle
        val fragmentManager = activity!!.supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container, weatherFragment, "WeatherFragment").commit()
    }
}
