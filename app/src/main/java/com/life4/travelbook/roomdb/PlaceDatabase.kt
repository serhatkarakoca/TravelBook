package com.life4.travelbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.life4.travelbook.model.Place

@Database(entities = [Place::class], version = 1)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDAO
}