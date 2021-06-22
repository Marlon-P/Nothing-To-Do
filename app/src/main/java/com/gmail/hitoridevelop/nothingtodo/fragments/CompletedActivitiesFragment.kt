package com.gmail.hitoridevelop.nothingtodo.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hitoridevelop.nothingtodo.R
import com.gmail.hitoridevelop.nothingtodo.adapters.ActivityListAdapter
import com.gmail.hitoridevelop.nothingtodo.data.Activity
import com.gmail.hitoridevelop.nothingtodo.data.ActivityViewModel
import com.gmail.hitoridevelop.nothingtodo.helpers.SwipeHelper
import com.google.android.material.snackbar.Snackbar

class CompletedActivitiesFragment : Fragment() {

    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivityListAdapter
    private lateinit var v: View
    private lateinit var removedItem: Activity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        v = inflater.inflate(R.layout.fragment_activity_list, container, false)
        initRecyclerView(v)
        return v
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.activity_list_rv)
        adapter = ActivityListAdapter(this)


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val divider = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        recyclerView.addItemDecoration(divider)

        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
        activityViewModel.getFinishedActivities.observe(viewLifecycleOwner, { list ->
            adapter.setData(list)
        })

        val itemTouchHelper = ItemTouchHelper(object: SwipeHelper(recyclerView){
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val action = adapter.getData()[position]
                return listOf(deleteButton(requireContext() , action, v, "Delete", position))
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun databaseActionDialog(
        context: Context,
        activity: Activity,
        v: View,
        dataBaseAction: String,
        position: Int
    ) {
        val builder = context.let { AlertDialog.Builder(it) }
        var message = ""
        var action = activity

        message = when (dataBaseAction) {
            "Update" -> "Complete Activity?"
            else -> "Delete Activity?"

        }
        builder.setMessage(message)
            .setPositiveButton(dataBaseAction) { _, _ ->
                when (dataBaseAction) {
                    "Update" -> {
                        action = Activity(activity.name, activity.type, activity.accessibilityRange,
                            activity.participantRange, activity.priceRange, 1)
                        activityViewModel.updateActivity(action)
                    }
                    else -> {//don't actually delete the activity from the db until the undo snackBar is dismissed
                        removedItem = adapter.remove(position)
                    }
                }
                undoDatabaseActionSnackBar(v, action, dataBaseAction, position)
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .create()
            .show()

    }

    @SuppressLint("ShowToast")
    private fun undoDatabaseActionSnackBar(v: View, action: Activity, dataBaseAction: String, position: Int) {
        val message = when(dataBaseAction) {
            "Update" -> "Completed Activity"
            else -> "Deleted Activity"
        }

        v.context?.let { context ->
            Snackbar.make(context, v, message, Snackbar.LENGTH_SHORT)
                .setDuration(5000)
                .setAction("UNDO") {
                    when (dataBaseAction) {
                        "Update" -> {
                            action.completed = 0
                            activityViewModel.updateActivity(action)
                        }
                        else -> {
                            adapter.add(removedItem, position)
                        }
                    }
                }
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        if (event == DISMISS_EVENT_TIMEOUT) {
                            if (dataBaseAction == "Delete") activityViewModel.deleteActivity(action)
                        }
                    }
                })
                .show()
        }
    }

    private fun deleteButton(
        context: Context,
        action: Activity,
        view: View,
        dataBaseAction: String,
        position: Int
    ): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    databaseActionDialog(context, action, view, dataBaseAction, position)
                }
            })
    }
}