package com.gmail.hitoridevelop.nothingtodo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hitoridevelop.nothingtodo.R
import com.gmail.hitoridevelop.nothingtodo.data.Activity
import com.gmail.hitoridevelop.nothingtodo.data.ActivityViewModel

class ActivityListAdapter(private val viewModelOwner: ViewModelStoreOwner) : RecyclerView.Adapter<ActivityListAdapter.ViewHolder>(){

    private var activityList =mutableListOf<Activity>()
    private lateinit var viewModel: ActivityViewModel


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        viewModel = ViewModelProvider(viewModelOwner).get(ActivityViewModel::class.java)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = activityList[position]
        holder.textView.text = currentItem.name


    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    fun setData(actions: List<Activity>) {
        activityList = actions as MutableList<Activity>
        notifyDataSetChanged()
    }

    fun getData(): List<Activity> {
        return activityList
    }

    fun remove(position: Int): Activity {
        val item = activityList.removeAt(position)
        notifyItemRemoved(position)
        return item
    }

    fun add(act: Activity, position: Int) {
        activityList.add(position, act)
        notifyItemInserted(position)
    }

}