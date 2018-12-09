package ru.beryukhov.retrofitcoroutinesexample

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        GlobalScope.launch(Dispatchers.IO) {
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

    private val BASE_URL = "https://api.rasp.yandex.net/v3.0"
    private val GET_UUID = "$BASE_URL/search"

    suspend fun getData(): List<Segment> {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        Log.d(TAG, SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))

        val apiKey = BuildConfig.YANDEX_API_KEY
        val odintsovoStationCode = "c10743"
        val belorusskiyRailTerminalCode = "s2000006"
        val suburbanType = "suburban"

        HttpClient() {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }.use {
            val data: TrainModel = it.get(
                "$GET_UUID/" +
                        "?apikey=$apiKey" +
                        "&from=$odintsovoStationCode" +
                        "&to=$belorusskiyRailTerminalCode" +
                        "&date=$currentDate" +
                        "&transport_types=$suburbanType" +
                        "&limit=150"
            )
            return data.segments!!
        }
    }
}
