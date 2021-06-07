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
    fun deleteActivity(activity: Activity)

    @Query("SELECT * FROM activities")
    fun getActivities(): LiveData<List<Activity>>

}