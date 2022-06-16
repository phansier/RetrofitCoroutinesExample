package ru.beryukhov.retrofitcoroutinesexample.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.beryukhov.retrofitcoroutinesexample.R


/**
 * Created by Andrey Beryukhov
 */
class TrainsListAdapter :
    ListAdapter<TrainItem, TrainViewHolder>(object : DiffUtil.ItemCallback<TrainItem>() {
        override fun areItemsTheSame(oldItem: TrainItem, newItem: TrainItem): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: TrainItem, newItem: TrainItem): Boolean =
            oldItem == newItem
    }) {
    override fun onBindViewHolder(holder: TrainViewHolder, position: Int) {
        val trainItem = getItem(position)
        holder.arrival.text = trainItem.arrival
        holder.departure.text = trainItem.departure
        if (trainItem.transportTitle.equals("Стандарт плюс", true)) {
            holder.transport_title.text = trainItem.transportTitle
            holder.transport_title.visibility = View.VISIBLE
        }
        holder.thread_title.text = trainItem.threadTitle
        if (trainItem.expressType != null) {
            holder.express_type.text = trainItem.expressType
            holder.express_type.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TrainViewHolder(inflater.inflate(R.layout.train_item, parent, false))
    }
}