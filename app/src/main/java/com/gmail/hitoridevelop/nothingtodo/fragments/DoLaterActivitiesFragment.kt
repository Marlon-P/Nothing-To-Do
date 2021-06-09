package com.gmail.hitoridevelop.nothingtodo.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hitoridevelop.nothingtodo.R
import com.gmail.hitoridevelop.nothingtodo.adapters.ActivityListAdapter
import com.gmail.hitoridevelop.nothingtodo.data.ActivityViewModel
import com.gmail.hitoridevelop.nothingtodo.helpers.SwipeHelper

class DoLaterActivitiesFragment : Fragment(){

    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivityListAdapter
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activity_list, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.activity_list_rv)
        adapter = ActivityListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(divider)

        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
        activityViewModel.getActivitiesToDo.observe(viewLifecycleOwner, { list ->
            adapter.setData(list)
        })

        val itemTouchHelper = ItemTouchHelper(object: SwipeHelper(recyclerView){
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {

                return listOf(deleteButton(requireContext(), position), completeButton(requireContext(), position))
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun toast(context: Context, text: String) {
        toast?.cancel()
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun completeButton(context: Context, position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            "Complete",
            14.0f,
            android.R.color.holo_green_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    toast(context, "Completed item $position")
                }
            })
    }

    private fun deleteButton(context: Context, position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    toast(context, "Deleted item $position")
                }
            })
    }
}