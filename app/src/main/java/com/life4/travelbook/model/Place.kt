package com.life4.travelbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Place(
    var name: String,
    var city: String,
    var year: Int,
    var photoURL: String,
    @PrimaryKey(autoGenerate = true)
    val place_id: Int? = null
)
