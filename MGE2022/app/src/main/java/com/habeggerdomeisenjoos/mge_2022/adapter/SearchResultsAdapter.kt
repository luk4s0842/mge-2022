package com.habeggerdomeisenjoos.mge_2022.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.activities.ArtistsActivity
import com.habeggerdomeisenjoos.mge_2022.activities.SearchArtistsActivity
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository
import com.habeggerdomeisenjoos.mge_2022.model.Artist
import com.squareup.picasso.Picasso

class SearchResultsAdapter(private val artists: List<Artist>) : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

    class SearchResultsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val description: TextView

        init {
            name = view.findViewById(R.id.artist_list_item_name)
            description = view.findViewById(R.id.artist_list_item_description)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.artists_list_item, viewGroup, false)

        return SearchResultsViewHolder(view)
    }

    override fun onBindViewHolder(artistViewHolder: SearchResultsViewHolder, position: Int) {
        artistViewHolder.name.text = artists[position].name
        artistViewHolder.description.text = artists[position].description
        artistViewHolder.itemView.setOnClickListener{
            val context = artistViewHolder.itemView.context
            AppRepository.addArtist(artists[position])
            context.startActivity(Intent(context, ArtistsActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }
}