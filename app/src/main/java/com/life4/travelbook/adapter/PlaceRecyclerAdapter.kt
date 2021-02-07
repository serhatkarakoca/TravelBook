package com.life4.travelbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.life4.travelbook.R
import com.life4.travelbook.model.Place
import javax.inject.Inject

class PlaceRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<PlaceRecyclerAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)

    private val diffUtil = object : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiff = AsyncListDiffer(this, diffUtil)

    var places: List<Place>
        get() = recyclerListDiff.currentList
        set(value) = recyclerListDiff.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_row, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.placesRowImageView)
        val placeName = holder.itemView.findViewById<TextView>(R.id.placesRowNameText)
        val cityName = holder.itemView.findViewById<TextView>(R.id.placesRowCityText)
        val yearText = holder.itemView.findViewById<TextView>(R.id.placesRowYearText)
        val place = places[position]
        holder.itemView.apply {
            glide.load(place.photoURL).into(imageView)
            placeName.text = place.name
            cityName.text = place.city
            yearText.text = place.year.toString()
        }
    }

    override fun getItemCount(): Int {
        return places.size
    }


}