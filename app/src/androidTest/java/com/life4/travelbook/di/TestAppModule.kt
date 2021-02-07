package com.life4.travelbook.di

import android.content.Context
import androidx.room.Room
import com.life4.travelbook.roomdb.PlaceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {


    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context: Context) =

        Room.inMemoryDatabaseBuilder(context,PlaceDatabase::class.java)
            .allowMainThreadQueries().build()


}