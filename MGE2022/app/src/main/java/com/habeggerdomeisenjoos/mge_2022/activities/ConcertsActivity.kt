package com.habeggerdomeisenjoos.mge_2022.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.activities.adapter.EventsAdapter
import com.habeggerdomeisenjoos.mge_2022.activities.model.Event
import java.time.LocalDateTime


class ConcertsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        var data = ArrayList<Event>()
        data.add(Event("Event 1", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 2", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 3", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 4", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 5", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 6", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 7", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 8", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 9", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 10", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 11", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))
        data.add(Event("Event 12", "Artist name", "Zürich", LocalDateTime.now(), "https://i.imgur.com/DvpvklR.png"))

        var recyclerView = findViewById<RecyclerView>(R.id.events_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EventsAdapter(data)
        recyclerView.adapter = adapter
    }
}