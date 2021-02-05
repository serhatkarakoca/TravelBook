package com.life4.travelbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.life4.artbook.R
import com.life4.artbook.databinding.FragmentPlaceDetailsBinding
import com.life4.artbook.databinding.FragmentPlacesBinding
import com.life4.travelbook.util.Status
import com.life4.travelbook.viewmodel.PlaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TravelDetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_place_details) {

    lateinit var viewModel: PlaceViewModel
    private var fragmentBinding: FragmentPlaceDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)
        val binding = FragmentPlaceDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.placeImageView.setOnClickListener {
            findNavController().navigate(TravelDetailsFragmentDirections.actionTravelDetailsFragmentToİmageApiFragment())
        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setSelectedImage("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.saveButton.setOnClickListener {
            viewModel.makePlace(binding.nameText.text.toString(),binding.cityText.text.toString(),binding.yearText.text.toString())
        }

    }
    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {url->
            fragmentBinding?.let {
                glide.load(url).into(it.placeImageView)
            }
        })

        viewModel.insertPlaceMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertPlaceMsg()
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }
                Status.LOADING ->{

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null

    }
}