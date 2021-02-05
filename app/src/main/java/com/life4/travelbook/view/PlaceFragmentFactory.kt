package com.life4.travelbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.life4.travelbook.adapter.ImageRecyclerAdapter
import com.life4.travelbook.adapter.PlaceRecyclerAdapter
import javax.inject.Inject

class PlaceFragmentFactory @Inject constructor(
    private val glide:RequestManager,
    private val placeRecyclerAdapter: PlaceRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            TravelFragment::class.java.name -> TravelFragment(placeRecyclerAdapter)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            TravelDetailsFragment::class.java.name -> TravelDetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}