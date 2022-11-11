package com.habeggerdomeisenjoos.mge_2022

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.habeggerdomeisenjoos.mge_2022.activities.model.Artist
import com.habeggerdomeisenjoos.mge_2022.activities.model.Event
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TMApiWrapper { // static class or singleton
    companion object {
        private const val API_VERSION: String = "v2"

        //packages
        private const val API_PACKAGE_DISCOVERY: String = "discovery"
        private const val API_KEY: String = "1I9lI5tLamHXGMY1IDXiZaVS1IcGnpDV"

        // API params
        private const val API_PARAM_KEYWORD: String = "keyword"
        private const val API_PARAM_TICKETSOURCE: String = "source"
        private const val API_PARAM_COUNTRYCODE: String = "countryCode"
        private const val API_PARAM_CLASSIFICATIONNAME: String = "classificationName"

        // possible values for API param classificationName
        private const val API_CLASSIFICATIONNAME_VALUE_MUSIC: String = "music"
        private const val API_CLASSIFICATIONNAME_VALUE_FILM: String = "film"
        private val BASE_URL: String = "https://app.ticketmaster.com/$API_PACKAGE_DISCOVERY/$API_VERSION/{resource}.json?apikey=$API_KEY"

        private lateinit var queue: RequestQueue
        private var INSTANCE: TMApiWrapper? = null


        fun getInstance(context: Context): TMApiWrapper = INSTANCE ?: synchronized(this) {
            queue = Volley.newRequestQueue(context.applicationContext)
            return TMApiWrapper()
        }
    }

    // ticket sellers
    enum class ticketSources {
        ticketmaster,
        universe,
        frontGate,
        ticketmaster_resale,
    }
    enum class classificationNames { //not sure if that works, example very bad
        music,
        film,
    }

    private fun createRequestUrl(
        sources: List<ticketSources>? = null,
        countryCode: String? = null,
        classificationName: classificationNames? = null){
    }


    private fun makeRequest(resource: String, params: HashMap<String, String>, onResponse: Response.Listener<JSONObject>) {
        var paramsStr = params.entries.stream()
            .map { e: Map.Entry<String, String> -> e.key + "=" + e.value }
            .toArray()
            .joinToString("&")
        var url = BASE_URL.replace("{resource}", resource) + "&" + paramsStr

        val request = JsonObjectRequest(Request.Method.GET, url, null, onResponse, { println("an error occured") })
        queue.add(request)
    }

    fun searchArtists(query: String, callback: (artists: ArrayList<Artist>) -> Unit) {
        var params = hashMapOf("keyword" to query)
        makeRequest("attractions", params) { response ->
            val artists = ArrayList<Artist>()
            val artistsJSON = response.getJSONObject("_embedded").getJSONArray("attractions")

            for (i in 0 until artistsJSON.length()) {
                val artist = artistsJSON.getJSONObject(i)
                artists.add(Artist(artist.getString("name"), artist.getString("id")))
            }
            callback(artists)
        }
    }

    fun getEvents(artist: Artist, callback: (events: ArrayList<Event>) -> Unit) {
        var params = hashMapOf("attractionId" to artist.id)

        makeRequest("events", params) { response ->
            val events = ArrayList<Event>()
            val eventsJSON = response.getJSONObject("_embedded").getJSONArray("events")

            for (i in 0 until eventsJSON.length()) {
                val eventJSON = eventsJSON.getJSONObject(i)
                val location = eventJSON
                    .getJSONObject("_embedded")
                    .getJSONArray("venues")
                    .getJSONObject(0)
                    .getJSONObject("address")
                    .getString("line1")

                val dateStr = eventJSON
                    .getJSONObject("dates")
                    .getJSONObject("start")
                    .getString("dateTime")

                val image = eventJSON
                    .getJSONArray("images")
                    .getJSONObject(1)
                    .getString("url")

                val event = Event(
                    eventJSON.getString("name"),
                    artist.name,
                    location,
                    LocalDateTime.parse(dateStr.substring(0, dateStr.length-1)),
                    image,
                )
                events.add(event)
            }
            callback(events)
        }
    }
}