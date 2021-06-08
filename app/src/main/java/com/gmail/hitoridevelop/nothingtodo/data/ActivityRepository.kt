package com.gmail.hitoridevelop.nothingtodo.data

import androidx.lifecycle.LiveData

class ActivityRepository(private val activityDao: ActivityDao) {

    val getActivities: LiveData<List<Activity>> = activityDao.getActivities()

    suspend fun addActivity(activity: Activity) {
        activityDao.addActivity(activity)
    }

    suspend fun deleteActivity(activity: Activity) {
        activityDao.deleteActivity(activity)
    }
}