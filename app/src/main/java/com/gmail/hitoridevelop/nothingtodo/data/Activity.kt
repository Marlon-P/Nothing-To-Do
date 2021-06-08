package com.gmail.hitoridevelop.nothingtodo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 from boredapi.com
 type: ["education", "recreational", "social", "diy", "charity", "cooking", "relaxation", "music", "busywork"]
 accessibility: [0.0, 1.0]
 participants: [0, n] (though 8 seems to be the highest n with a valid response for now)
 price: [0.0, 1.0] //0 being free

 //api also includes a key as in a unique id for each activity and a link for some activities, but I don't
 think they were needed for my use cases for now
  */
@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey val name: String,
    val type: String,
    val accessibilityRange: String,
    val participantRange: String,
    val priceRange: String,
    var completed: Int //0 being false 1 being true
)
