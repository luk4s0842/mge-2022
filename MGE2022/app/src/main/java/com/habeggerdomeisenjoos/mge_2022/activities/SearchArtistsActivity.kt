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
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.isNotEmpty()) {
                    TMApiWrapper.getInstance().searchArtists(newText){ result -> updateSearchResults(recyclerView, result) }
                    return true
                }
                return false
            }
        })
    }

    fun updateSearchResults (recyclerView: RecyclerView, results: ArrayList<Artist>) {
        if (results.isEmpty()) {
            Snackbar
                .make(recyclerView, "Nothing found!", Snackbar.LENGTH_LONG)
                .show()
        }
        val adapter = SearchResultsAdapter(results)
        recyclerView.adapter = adapter
    }
}