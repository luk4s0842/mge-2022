package com.habeggerdomeisenjoos.mge_2022.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.model.Event
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter


class EventsAdapter(private val events: ArrayList<Event>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById(R.id.event_title)
        var imageView: ImageView = itemView.findViewById(R.id.event_image)
        var artistView: TextView = itemView.findViewById(R.id.event_artist)
        var locationView: TextView = itemView.findViewById(R.id.event_location)
        var datetimeView: TextView = itemView.findViewById(R.id.event_datetime)

        fun bind(event: Event) {
            titleView.text = event.title
            artistView.text = event.artistName
            locationView.text = event.location

            var formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
            datetimeView.text = event.datetime.format(formatter)

            Picasso.get().load(event.imageUrl).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = this.events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return events.size
    }
}