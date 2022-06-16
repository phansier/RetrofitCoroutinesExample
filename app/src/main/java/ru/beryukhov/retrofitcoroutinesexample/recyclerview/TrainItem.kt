package ru.beryukhov.retrofitcoroutinesexample.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.beryukhov.retrofitcoroutinesexample.R

/**
 * Created by Andrey Beryukhov
 */
public class TrainItem (val departure:String?, val arrival:String?, val transportTitle:String?, val threadTitle:String?, val expressType:String?):
    IBaseListItem {
    override fun getLayoutId(): Int {
        return R.layout.train_item
    }

}

class TrainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val departure: TextView = view.findViewById(R.id.departure)
    val arrival: TextView = view.findViewById(R.id.arrival)
    val transport_title: TextView = view.findViewById(R.id.transport_title)
    val thread_title: TextView = view.findViewById(R.id.thread_title)
    val express_type: TextView = view.findViewById(R.id.express_type)
}