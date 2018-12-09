package ru.beryukhov.retrofitcoroutinesexample

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.beryukhov.retrofitcoroutinesexample.recyclerview.TrainItem
import ru.beryukhov.retrofitcoroutinesexample.recyclerview.TrainsListAdapter
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : Activity() {
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TrainsListAdapter()

        rasp.layoutManager = LinearLayoutManager(this)
        rasp.adapter = adapter

        GlobalScope.launch {
            val responce = getData()

            withContext(Dispatchers.Main) {
                for (train in responce) {
                    if (train.departure!! > (SimpleDateFormat("HH:mm").format(Date())))
                        adapter.add(
                            TrainItem(
                                train.departure,
                                train.arrival,
                                train.thread?.transport_subtype?.title,
                                train.thread?.title,
                                train.thread?.express_type
                            )
                        )
                }
            }
        }
    }

    private fun makeService(): TrainsInterface {
        val builder = Retrofit.Builder()
            .baseUrl("https://api.rasp.yandex.net/v3.0/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())

        return builder.build().create(TrainsInterface::class.java)

    }

    suspend fun getData(): List<Segment> {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        Log.d(TAG, SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))

        val responce = makeService().getElektrichki(
            BuildConfig.YANDEX_API_KEY,//get your token here https://tech.yandex.ru/rasp/raspapi/
            "c10743",//Odintsovo station
            "s2000006",//Belorusskiy railway station
            currentDate,
            "suburban",
            150
        ).await()
        return responce.segments!!
    }
}
