package com.life4.travelbook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.life4.travelbook.MainCoroutineRule
import com.life4.travelbook.getOrAwaitValueTest
import com.life4.travelbook.repo.FakePlaceRepository
import com.life4.travelbook.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PlaceViewModelTest {

    lateinit var viewModel:PlaceViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        viewModel = PlaceViewModel(FakePlaceRepository())
    }

    @Test
    fun `insert place without year returns error`(){
        viewModel.makePlace("Anitkabir","Ankara","")
        val value = viewModel.insertPlaceMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert place without name returns error`(){
        viewModel.makePlace("","Ankara","1999")
        val value = viewModel.insertPlaceMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert place without city returns error`(){
        viewModel.makePlace("Anitkabir","","1999")
        val value = viewModel.insertPlaceMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insert place without year not int returns error`(){
        viewModel.makePlace("Anitkabir","Ankara","Test")
        val value = viewModel.insertPlaceMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}