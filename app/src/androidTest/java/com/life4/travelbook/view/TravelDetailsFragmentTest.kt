package com.life4.travelbook.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.life4.travelbook.R
import com.life4.travelbook.getOrAwaitValue
import com.life4.travelbook.launchFragmentInHiltContainer
import com.life4.travelbook.model.Place
import com.life4.travelbook.repo.FakePlaceRepositoryTest
import com.life4.travelbook.viewmodel.PlaceViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class TravelDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: PlaceFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun detailsToImageApiFragmentWhenClickImage(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<TravelDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.placeImageView)).perform(click())

        Mockito.verify(navController).navigate(TravelDetailsFragmentDirections.actionTravelDetailsFragmentToİmageApiFragment())

    }

    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<TravelDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()

        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSaveButton(){

        val testViewModel = PlaceViewModel(FakePlaceRepositoryTest())

        launchFragmentInHiltContainer<TravelDetailsFragment>(factory = fragmentFactory){
            viewModel = testViewModel
        }
        Espresso.onView(ViewMatchers.withId(R.id.nameText)).perform(replaceText("Anitkabir"))
        Espresso.onView(ViewMatchers.withId(R.id.cityText)).perform(replaceText("Ankara"))
        Espresso.onView(ViewMatchers.withId(R.id.yearText)).perform(replaceText("2001"))
        Espresso.onView(ViewMatchers.withId(R.id.saveButton)).perform(click())

        assertThat(testViewModel.placeList.getOrAwaitValue()).contains(
            Place("Anitkabir","Ankara",2001,"")
        )
    }
}