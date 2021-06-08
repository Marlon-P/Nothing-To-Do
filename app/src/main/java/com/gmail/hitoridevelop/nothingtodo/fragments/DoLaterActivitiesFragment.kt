package com.gmail.hitoridevelop.nothingtodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        val adapter = ActivityListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
        activityViewModel.getActivities.observe(viewLifecycleOwner, { list ->
            adapter.setData(list)
        })
        return view
    }
}