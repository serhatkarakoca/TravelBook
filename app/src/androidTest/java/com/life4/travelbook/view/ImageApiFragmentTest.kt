package com.life4.travelbook.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.life4.travelbook.R
import com.life4.travelbook.adapter.ImageRecyclerAdapter
import com.life4.travelbook.getOrAwaitValue
import com.life4.travelbook.launchFragmentInHiltContainer
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
class ImageApiFragmentTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: PlaceFragmentFactory


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun selectImage() {
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = PlaceViewModel(FakePlaceRepositoryTest())
        val selectedImageUrl = "test.com"

        launchFragmentInHiltContainer<ImageApiFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
            imageRecyclerAdapter.images = listOf(selectedImageUrl)
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl)

    }

}