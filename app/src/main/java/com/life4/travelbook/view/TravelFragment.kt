package com.life4.travelbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.life4.artbook.R
import com.life4.artbook.databinding.FragmentPlacesBinding

class TravelFragment : Fragment(R.layout.fragment_places) {
    var fragmentBinding: FragmentPlacesBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPlacesBinding.bind(view)
        fragmentBinding = binding

        binding.fab.setOnClickListener {
            findNavController().navigate(TravelFragmentDirections.actionTravelFragmentToTravelDetailsFragment())
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()

    }
}