package com.jeleniep.publicationManager

import PublicationResponse
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jeleniep.publicationManager.model.publications.PublicationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        getCurrentData();
    }

    internal fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.19:3000/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PublicationService::class.java)
        val call = service.getCurrentWeatherData()
        call.enqueue(object : Callback<List<PublicationResponse>> {
            override fun onResponse(
                call: Call<List<PublicationResponse>>,
                response: Response<List<PublicationResponse>>
            ) {
                if (response.code() == 200) {
                    val publicationResponse = response.body()!!

                    for (publication in publicationResponse) {
                        Log.d("debug",  publication.name);
                        Log.d("debug",  "cos");

                    }
//                    val stringBuilder = "Country: " +
//                            weatherResponse.sys!!.country +
//                            "\n" +
//                            "Temperature: " +
//                            weatherResponse.main!!.temp +
//                            "\n" +
//                            "Temperature(Min): " +
//                            weatherResponse.main!!.temp_min +
//                            "\n" +
//                            "Temperature(Max): " +
//                            weatherResponse.main!!.temp_max +
//                            "\n" +
//                            "Humidity: " +
//                            weatherResponse.main!!.humidity +
//                            "\n" +
//                            "Pressure: " +
//                            weatherResponse.main!!.pressure
//
//                    weatherData!!.text = stringBuilder
                }
            }

            override fun onFailure(call: Call<List<PublicationResponse>>, t: Throwable) {
//                weatherData!!.text = t.message
                Log.d("debug", "ASdad" + t.message);

            }

        })
    }
}
