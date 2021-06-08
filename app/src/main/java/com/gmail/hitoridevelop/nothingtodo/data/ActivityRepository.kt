package com.gmail.hitoridevelop.nothingtodo.data

import androidx.lifecycle.LiveData

class ActivityRepository(private val activityDao: ActivityDao) {

    val getFinishedActivities: LiveData<List<Activity>> = activityDao.getFinishedActivities()
    val getActivitiesToDo: LiveData<List<Activity>> = activityDao.getActivitiesToDo()

    suspend fun addActivity(activity: Activity) {
        activityDao.addActivity(activity)
    }

    suspend fun deleteActivity(activity: Activity) {
        activityDao.deleteActivity(activity)
    }
}