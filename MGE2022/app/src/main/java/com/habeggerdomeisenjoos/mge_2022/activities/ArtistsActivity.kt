package com.habeggerdomeisenjoos.mge_2022.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.adapter.ArtistsAdapter
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository
import com.habeggerdomeisenjoos.mge_2022.model.Artist

class ArtistsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artists)

        val recyclerView = findViewById<RecyclerView>(R.id.artists_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val artistsList: ArrayList<Artist> = AppRepository.getArtists()

        val adapter = ArtistsAdapter(artistsList)
        recyclerView.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.artists_add_artist)
        fab.setOnClickListener {
            startActivity(Intent(this, SearchArtistsActivity::class.java))
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val deletedArtist: Artist = artistsList[position]

                AppRepository.deleteArtist(deletedArtist)
                artistsList.removeAt(position)

                adapter.notifyItemRemoved(position)
            }

        }).attachToRecyclerView(recyclerView)
    }
}