package com.life4.travelbook.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.life4.artbook.R
import com.life4.travelbook.api.RetrofitAPI
import com.life4.travelbook.repo.PlaceRepository
import com.life4.travelbook.repo.PlaceRepositoryInterface
import com.life4.travelbook.roomdb.PlaceDAO
import com.life4.travelbook.roomdb.PlaceDatabase
import com.life4.travelbook.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

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
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao:PlaceDAO,api:RetrofitAPI) = PlaceRepository(dao,api) as PlaceRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context)   = Glide
        .with(context)
        .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
        )


}