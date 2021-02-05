package com.life4.travelbook.repo

import androidx.lifecycle.LiveData
import com.life4.travelbook.model.ImageResponse
import com.life4.travelbook.model.Place
import com.life4.travelbook.util.Resource

interface PlaceRepositoryInterface {

    suspend fun insertPlace(place:Place)

    suspend fun deletePlace(place:Place)

    fun getPlace():LiveData<List<Place>>

    suspend fun imageSearch(imageUrl:String):Resource<ImageResponse>
}