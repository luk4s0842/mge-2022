package com.habeggerdomeisenjoos.mge_2022.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.TMApiWrapper
import com.habeggerdomeisenjoos.mge_2022.adapter.EventsAdapter
import com.habeggerdomeisenjoos.mge_2022.model.Artist
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository
import com.habeggerdomeisenjoos.mge_2022.model.Event


class ConcertsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        
        var recyclerView = findViewById<RecyclerView>(R.id.events_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var currentEvents = ArrayList<Event>()
        var artists = AppRepository.getArtists()
        for (artist in artists) {
            println(artist.tmId)
            TMApiWrapper.getInstance().getEventsFromArtist(artist) { events: ArrayList<Event> ->
                currentEvents.addAll(events)
                val adapter = EventsAdapter(getSortedEvents(currentEvents))
                recyclerView.adapter = adapter
            }
        }
    }

    private fun getSortedEvents(events: ArrayList<Event>) : ArrayList<Event> {
        return ArrayList(events.sortedBy { it.datetime })
    }
}
