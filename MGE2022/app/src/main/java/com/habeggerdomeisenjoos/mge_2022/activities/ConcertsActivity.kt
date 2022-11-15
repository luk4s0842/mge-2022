package com.habeggerdomeisenjoos.mge_2022.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.TMApiWrapper
import com.habeggerdomeisenjoos.mge_2022.activities.adapter.EventsAdapter
import com.habeggerdomeisenjoos.mge_2022.activities.model.Artist
import com.habeggerdomeisenjoos.mge_2022.activities.model.Event
import java.time.LocalDateTime


class ConcertsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        
        var recyclerView = findViewById<RecyclerView>(R.id.events_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var artist = Artist("Test", "K8vZ9171oZf")
        TMApiWrapper.getInstance(this).getEventsFromArtist(artist) { events: ArrayList<Event> ->
            val adapter = EventsAdapter(events)
            recyclerView.adapter = adapter
        }
    }
}