package com.life4.travelbook.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.life4.travelbook.R
import com.life4.travelbook.databinding.FragmentPlacesBinding
import com.life4.travelbook.adapter.PlaceRecyclerAdapter
import com.life4.travelbook.viewmodel.PlaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.androidexception.andexalertdialog.AndExAlertDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TravelFragment @Inject constructor(
    val placeRecyclerAdapter: PlaceRecyclerAdapter
) : Fragment(R.layout.fragment_places) {

   private  var fragmentBinding: FragmentPlacesBinding? = null
    lateinit var viewModel: PlaceViewModel


    val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val swipePosition = viewHolder.layoutPosition
            val place = placeRecyclerAdapter.places[swipePosition]


            AndExAlertDialog.Builder(requireContext())
                 .setTitle("Have you ever been at ${place.name.toUpperCase()}")
                .setMessage("Do you really want to Delete ?")
                .setPositiveBtnText("Delete")
                .setNegativeBtnText("Cancel")
                .setCancelableOnTouchOutside(false)
                .setImage(R.drawable.ic_baseline_close_24,15)
                .setButtonTextColor(R.color.light_red)
                .OnNegativeClicked {
                    placeRecyclerAdapter.notifyDataSetChanged()
                }
                .OnPositiveClicked {
                    viewModel.deletePlace(place)
                }

                .build()

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)

        val binding = FragmentPlacesBinding.bind(view)
        fragmentBinding = binding


        subscribeToObservers()

        binding.recyclerViewPlaces.adapter = placeRecyclerAdapter
        binding.recyclerViewPlaces.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewPlaces)

        binding.fab.setOnClickListener {
            findNavController().navigate(TravelFragmentDirections.actionTravelFragmentToTravelDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.placeList.observe(viewLifecycleOwner, Observer {
            placeRecyclerAdapter.places = it
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()

    }
}