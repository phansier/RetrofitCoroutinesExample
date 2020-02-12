package ru.beryukhov.retrofitcoroutinesexample

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.*
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
                val currentTime = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
                for (train in responce) {
                    if (train.departure!! > currentTime)
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

        val apiKey = BuildConfig.YANDEX_API_KEY
        val odintsovoStationCode = "c10743"
        val belorusskiyRailTerminalCode = "s2000006"
        val suburbanType = "suburban"

        HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = /*if (BuildConfig.DEBUG) BODY else*/ NONE
            engine {
                addInterceptor(interceptor)
            }
        }.use {
            val data: TrainModel = it.get(
                "$GET_UUID/" +
                        "?apikey=$apiKey" +
                        "&from=$odintsovoStationCode" +
                        "&to=$belorusskiyRailTerminalCode" +
                        "&date=$currentDate" +
                        "&transport_types=$suburbanType" +
                        "&limit=300"
            )
            return data.segments!!
        }
    }
}
