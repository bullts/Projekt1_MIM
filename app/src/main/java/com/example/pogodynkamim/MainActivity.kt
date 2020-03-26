package com.example.pogodynkamim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.Exception
import java.net.URL
import android.os.StrictMode
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy) //na probe chyba wazne

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, WeatherFragment())
                .commit()
        }



    }
}


