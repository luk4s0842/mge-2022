package com.habeggerdomeisenjoos.mge_2022.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.model.Artist

class ArtistsAdapter(private val artists: Array<Artist>) : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {


    class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val picture: ImageView
        val name: TextView
        val description: TextView

        init {
            picture = view.findViewById(R.id.artist_list_item_picture)
            name = view.findViewById(R.id.artist_list_item_title)
            description = view.findViewById(R.id.artist_list_item_description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.artists_list_item, parent, false)

        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(artistViewHolder: ArtistViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return artists.size
    }
}