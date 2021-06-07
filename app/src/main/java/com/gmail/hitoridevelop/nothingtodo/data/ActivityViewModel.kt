package com.gmail.hitoridevelop.nothingtodo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityViewModel(application: Application): AndroidViewModel(application) {
    private val getActivities: LiveData<List<Activity>>
    private val repository: ActivityRepository

    init {
        val activityDao = ActivityDatabase.getDatabase(application).activityDao()
        repository = ActivityRepository(activityDao)
        getActivities = repository.getActivities
    }

    fun addActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addActivity(activity)
        }
    }

}