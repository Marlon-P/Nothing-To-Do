package com.gmail.hitoridevelop.nothingtodo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityViewModel(application: Application): AndroidViewModel(application) {
    val getActivitiesToDo: LiveData<List<Activity>>
    val getFinishedActivities: LiveData<List<Activity>>

    private val repository: ActivityRepository

    init {
        val activityDao = ActivityDatabase.getDatabase(application).activityDao()
        repository = ActivityRepository(activityDao)
        getActivitiesToDo = repository.getActivitiesToDo
        getFinishedActivities = repository.getFinishedActivities
    }

    fun addActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addActivity(activity)
        }
    }

    fun deleteActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteActivity(activity)
        }
    }

    fun updateActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateActivity(activity)
        }
    }
}