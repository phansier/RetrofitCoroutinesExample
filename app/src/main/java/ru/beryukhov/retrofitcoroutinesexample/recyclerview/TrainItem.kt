package ru.beryukhov.retrofitcoroutinesexample.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.train_item.view.*
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
    val departure = view.departure
    val arrival = view.arrival
    val transport_title = view.transport_title
    val thread_title = view.thread_title
    val express_type = view.express_type
}