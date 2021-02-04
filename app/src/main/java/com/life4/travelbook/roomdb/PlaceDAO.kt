package com.life4.travelbook.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.life4.travelbook.model.Place


@Dao
interface PlaceDAO {

    @Insert
    suspend fun insertPlace(place: Place)

    @Delete
    suspend fun deletePlace(place: Place)

    @Query("SELECT * FROM places")
    fun observePlaces(): LiveData<List<Place>>
}