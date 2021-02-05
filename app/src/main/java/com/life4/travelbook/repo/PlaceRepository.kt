package com.life4.travelbook.repo

import androidx.lifecycle.LiveData
import com.life4.travelbook.api.RetrofitAPI
import com.life4.travelbook.model.ImageResponse
import com.life4.travelbook.model.Place
import com.life4.travelbook.roomdb.PlaceDAO
import com.life4.travelbook.util.Resource
import java.lang.Exception
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val placeDAO: PlaceDAO,
    private val retrofitAPI: RetrofitAPI
) : PlaceRepositoryInterface {
    override suspend fun insertPlace(place: Place) {
        placeDAO.insertPlace(place)
    }

    override suspend fun deletePlace(place: Place) {
        placeDAO.deletePlace(place)
    }

    override fun getPlace(): LiveData<List<Place>> {
        return placeDAO.observePlaces()
    }

    override suspend fun imageSearch(imageUrl: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.searchImage(imageUrl)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }


    }


}