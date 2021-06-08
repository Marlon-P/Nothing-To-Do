package com.gmail.hitoridevelop.nothingtodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hitoridevelop.nothingtodo.R
import com.gmail.hitoridevelop.nothingtodo.adapters.ActivityListAdapter
import com.gmail.hitoridevelop.nothingtodo.data.ActivityViewModel

class DoLaterActivitiesFragment : Fragment(){

    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activity_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_list_rv)
        val adapter = ActivityListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val divider = DividerItemDecoration(context, (recyclerView.layoutManager as LinearLayoutManager).layoutDirection)
        recyclerView.addItemDecoration(divider)

        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
        activityViewModel.getActivitiesToDo.observe(viewLifecycleOwner, { list ->
            adapter.setData(list)
        })
        return view
    }
}