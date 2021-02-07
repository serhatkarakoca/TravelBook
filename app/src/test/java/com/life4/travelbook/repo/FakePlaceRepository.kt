package com.life4.travelbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.life4.travelbook.model.ImageResponse
import com.life4.travelbook.model.Place
import com.life4.travelbook.util.Resource

class FakePlaceRepository : PlaceRepositoryInterface {

    private val places = mutableListOf<Place>()
    private val placesLiveData = MutableLiveData<List<Place>>(places)


    override suspend fun insertPlace(place: Place) {
        places.add(place)
        refreshData()
    }

    override suspend fun deletePlace(place: Place) {
        places.remove(place)
        refreshData()
    }

    override fun getPlace(): LiveData<List<Place>> {
        return placesLiveData
    }

    override suspend fun imageSearch(imageUrl: String): Resource<ImageResponse> {
      return Resource.success(ImageResponse(listOf(),0,0))
    }

    fun refreshData(){
        placesLiveData.postValue(places)
    }
}