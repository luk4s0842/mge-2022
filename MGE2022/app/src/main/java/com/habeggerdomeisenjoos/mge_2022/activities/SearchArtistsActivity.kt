package com.habeggerdomeisenjoos.mge_2022.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.TMApiWrapper
import com.habeggerdomeisenjoos.mge_2022.adapter.ArtistsAdapter
import com.habeggerdomeisenjoos.mge_2022.adapter.SearchResultsAdapter
import com.habeggerdomeisenjoos.mge_2022.model.Artist

class SearchArtistsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_artists)

        val recyclerView = findViewById<RecyclerView>(R.id.search_artists_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchBar = findViewById<SearchView>(R.id.search_artists_searchbar)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // TODO: TMApiWrapper has to return correct object
                    // TMApiWrapper().searchArtists(query){result -> updateSearchResults(recyclerView, result)}
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false;
            }
        })

        // TODO: Remove test data below as soon as TODO on line 34 is done
        // -----------   Test  -----------
        val artist1 = Artist("22", "Post Malone", "A crazy dude", "https://www.morecore.de/wp-content/uploads/post_malone_saint_tropez_video-scaled.jpg")
        val artist2 = Artist("24", "Tyga", "Another crazy dude", "https://image.gala.de/22872200/t/K7/v3/w1440/r1/-/tyga.jpg")
        val results = ArrayList<Artist>()
        results.add(artist1)
        results.add(artist2)
        updateSearchResults(recyclerView, results)
        // ----------- Test End ----------
    }

    fun updateSearchResults (recyclerView: RecyclerView, results: ArrayList<Artist>) : Boolean{
        if (results.isEmpty()) {
            Snackbar
                .make(recyclerView, "Nothing found!", Snackbar.LENGTH_LONG)
                .show()
            return false
        } else {
            val adapter = SearchResultsAdapter(results)
            recyclerView.adapter = adapter
            return true
        }
    }
}