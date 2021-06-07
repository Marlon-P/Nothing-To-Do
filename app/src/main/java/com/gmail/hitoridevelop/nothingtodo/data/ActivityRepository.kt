package com.gmail.hitoridevelop.nothingtodo.data

import androidx.lifecycle.LiveData

class ActivityRepository(private val activityDao: ActivityDao) {

    val getActivities: LiveData<List<Activity>> = activityDao.getActivities()

    fun addActivity(activity: Activity) {

    }
}