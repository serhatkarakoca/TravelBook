package com.life4.travelbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.life4.travelbook.getOrAwaitValue
import com.life4.travelbook.model.Place
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class PlaceDaoTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: PlaceDatabase
    lateinit var dao: PlaceDAO

    @Before
    fun setup() {

        hiltRule.inject()
        dao = database.placeDao()
    }

    @Test
    fun insertPlaceTesting() = runBlockingTest{
        val examplePlace = Place("Anitkabir","Ankara",1999,"Test.com",1)
        dao.insertPlace(examplePlace)
        val list = dao.observePlaces().getOrAwaitValue()
        assertThat(list).contains(examplePlace)
    }

    @Test
    fun deletePlaceTesting()= runBlockingTest{
        val examplePlace = Place("Anitkabir","Ankara",1999,"Test.com",1)
        dao.insertPlace(examplePlace)
        dao.deletePlace(examplePlace)
        val list = dao.observePlaces().getOrAwaitValue()
        assertThat(list).doesNotContain(examplePlace)
    }



    @After
    fun tearDown() {
        database.close()
    }
}
