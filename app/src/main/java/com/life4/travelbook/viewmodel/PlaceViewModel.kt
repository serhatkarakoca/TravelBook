package com.life4.travelbook.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.life4.travelbook.model.ImageResponse
import com.life4.travelbook.model.Place
import com.life4.travelbook.repo.PlaceRepositoryInterface
import com.life4.travelbook.util.Resource
import kotlinx.coroutines.launch
import java.lang.Exception


class PlaceViewModel @ViewModelInject constructor(
    private val repository: PlaceRepositoryInterface
) : ViewModel() {

    val placeList = repository.getPlace()

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    private var insertPlaceMsg = MutableLiveData<Resource<Place>>()
    val insertPlaceMessage: LiveData<Resource<Place>>
        get() = insertPlaceMsg

    //Solving the navigation bug
    fun resetInsertPlaceMsg() {
        insertPlaceMsg = MutableLiveData<Resource<Place>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun deletePlace(place: Place) = viewModelScope.launch {
        repository.deletePlace(place)
    }

    fun insertPlace(place: Place) = viewModelScope.launch {
        repository.insertPlace(place)
    }

    fun makePlace(name:String,city:String,year:String){
        if (name.isEmpty()||city.isEmpty()||year.isEmpty()){
            insertPlaceMsg.postValue(Resource.error("Enter name, city, year",null))
            return
        }
        val yearInt:Int = try {
            year.toInt()
        }catch (e:Exception){
            insertPlaceMsg.postValue(Resource.error("Year must be number",null))
            return
        }
        val place = Place(name,city,yearInt,selectedImage.value?:"")
        insertPlace(place)
        setSelectedImage("")
        insertPlaceMsg.postValue(Resource.success(place))
    }


    fun searchForImage(searchString: String) {
        if(searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.imageSearch(searchString)
            images.value = response
        }


    }
}


