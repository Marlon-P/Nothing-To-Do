package com.gmail.hitoridevelop.nothingtodo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/*
    Data Access Object for the Activity class
*/
@Dao
interface ActivityDao {
    @Insert
    suspend fun addActivity(activity: Activity)

    @Delete
    suspend fun deleteActivity(activity: Activity)

    @Query("SELECT * FROM activities WHERE completed = 0")
    fun getActivitiesToDo(): LiveData<List<Activity>>

    @Query("SELECT * FROM activities WHERE completed = 1")
    fun getFinishedActivities(): LiveData<List<Activity>>

}