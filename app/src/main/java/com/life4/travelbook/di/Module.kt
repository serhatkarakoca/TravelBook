package com.life4.travelbook.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.life4.travelbook.api.RetrofitAPI
import com.life4.travelbook.roomdb.PlaceDatabase
import com.life4.travelbook.util.Util.BASE_URL
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
object Module {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PlaceDatabase::class.java, "PlacesDB").build()

    @Singleton
    @Provides
    fun injectDao(database: PlaceDatabase) = database.placeDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitAPI::class.java)
    }

}