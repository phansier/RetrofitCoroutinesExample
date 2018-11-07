package ru.beryukhov.retrofitcoroutinesexample.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.beryukhov.retrofitcoroutinesexample.R


/**
 * Created by Andrey Beryukhov
 */
class TrainsListAdapter : SimpleListAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val context = parent.context

        return when (viewType) {
            R.layout.train_item -> TrainViewHolder(inflateByViewType(context, viewType, parent))
            else -> throw IllegalStateException("There is no match with current layoutId")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {

            is TrainViewHolder -> {
                val trainItem = items[position] as TrainItem
                holder.arrival.text = trainItem.arrival
                holder.departure.text = trainItem.departure
                if(trainItem.transportTitle.equals("Стандарт плюс",true)){
                    holder.transport_title.text = trainItem.transportTitle
                    holder.transport_title.visibility=View.VISIBLE
                }
                holder.thread_title.text = trainItem.threadTitle
                if (trainItem.expressType != null) {
                    holder.express_type.text = trainItem.expressType
                    holder.express_type.visibility = View.VISIBLE
                }
            }

            else -> throw IllegalStateException("There is no match with current holder instance")
        }
    }
}