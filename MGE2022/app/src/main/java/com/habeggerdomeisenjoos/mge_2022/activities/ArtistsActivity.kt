package com.habeggerdomeisenjoos.mge_2022.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.adapter.ArtistsAdapter
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository

class ArtistsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artists)

        val recyclerView = findViewById<RecyclerView>(R.id.artists_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ArtistsAdapter(AppRepository.getArtists())
        recyclerView.adapter = adapter
    }
}