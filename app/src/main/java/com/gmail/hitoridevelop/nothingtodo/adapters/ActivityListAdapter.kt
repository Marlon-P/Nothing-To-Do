package com.gmail.hitoridevelop.nothingtodo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hitoridevelop.nothingtodo.R
import com.gmail.hitoridevelop.nothingtodo.data.Activity
import com.gmail.hitoridevelop.nothingtodo.data.ActivityViewModel

class ActivityListAdapter(val viewModelOwner: ViewModelStoreOwner) : RecyclerView.Adapter<ActivityListAdapter.ViewHolder>(){

    private var activityList = emptyList<Activity>()
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
        holder.textView.text = currentItem.type

        val completed = currentItem.completed
        holder.itemView.setOnClickListener{
            updateActivityDialog(completed, it.context, currentItem)
        }
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    fun setData(actions: List<Activity>) {
        activityList = actions
        notifyDataSetChanged()
    }

    private fun updateActivityDialog(finished: Int, context: Context, activity: Activity) {
        val builder = context.let { AlertDialog.Builder(it) }
        builder.setMessage("Move Activity?")
            .setPositiveButton("UPDATE") { _, _ ->
                when (finished) {
                    0 -> {
                        val action = Activity(activity.name, activity.type, activity.accessibilityRange,
                                            activity.participantRange, activity.priceRange, 1)
                        viewModel.updateActivity(action)
                    }
                    else -> {
                        val action = Activity(activity.name, activity.type, activity.accessibilityRange,
                            activity.participantRange, activity.priceRange, 0)
                        viewModel.updateActivity(action)
                    }
                }
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .create()
            .show()

    }

}