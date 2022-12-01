package com.habeggerdomeisenjoos.mge_2022.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.model.Artist
import com.squareup.picasso.Picasso

class ArtistsAdapter(private val artists: ArrayList<Artist>) : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {
    class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val picture: ImageView
        val name: TextView
        val description: TextView

        init {
            picture = view.findViewById(R.id.artist_list_item_picture)
            name = view.findViewById(R.id.artist_list_item_name)
            description = view.findViewById(R.id.artist_list_item_description)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.artists_list_item, viewGroup, false)

        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(artistViewHolder: ArtistViewHolder, position: Int) {
        Picasso.get().load(artists[position].pictureUrl).into(artistViewHolder.picture)
        artistViewHolder.name.text = artists[position].name
        artistViewHolder.description.text = artists[position].description
    }

    override fun getItemCount(): Int {
        return artists.size
    }
}