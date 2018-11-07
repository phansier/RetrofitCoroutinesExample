package ru.beryukhov.retrofitcoroutinesexample.recyclerview

/**
 * Created by Andrey Beryukhov
 */
interface IBaseListAdapter<T> {
    fun add(newItem: T)
    fun add(newItems: ArrayList<T>?)
    fun addAtPosition(pos : Int, newItem : T)
    fun remove(position: Int)
    fun clearAll()
}