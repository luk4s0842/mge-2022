package com.habeggerdomeisenjoos.mge_2022.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.habeggerdomeisenjoos.mge_2022.R

class ArtistsAdapter(private val artists: Array<String>) : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {


    class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ArtistViewHolder(view, )
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.textView.text = artists[position]
    }

    override fun getItemCount(): Int {
        return artists.size
    }
}