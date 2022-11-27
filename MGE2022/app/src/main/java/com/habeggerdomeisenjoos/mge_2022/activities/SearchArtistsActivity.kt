package com.habeggerdomeisenjoos.mge_2022.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.TMApiWrapper
import com.habeggerdomeisenjoos.mge_2022.model.Artist

class SearchArtistsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_artists)

        val searchBar = findViewById<SearchView>(R.id.search_artists_searchbar)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // TODO: Implement displaying search results
                    //TMApiWrapper().searchArtists(query){result -> updateSearchResults(result)}
                    return true
                } else {
                    // TODO: Display nothing found with snackbar
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false;
            }
        })
    }

    fun updateSearchResults (results: ArrayList<Artist>){

    }
}