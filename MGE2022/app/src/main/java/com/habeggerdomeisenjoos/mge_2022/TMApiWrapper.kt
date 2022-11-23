package com.habeggerdomeisenjoos.mge_2022

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.habeggerdomeisenjoos.mge_2022.activities.model.Artist
import com.habeggerdomeisenjoos.mge_2022.activities.model.Event
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime

class TMApiWrapper { // static class or singleton
    companion object {
        private const val API_VERSION: String = "v2"

        //Resources
        private const val API_RESOURCE_EVENTS: String = "events"
        private const val API_RESOURCE_ATTRACTION: String = "attractions"

        //packages
        private const val API_PACKAGE_DISCOVERY: String = "discovery"
        private const val API_KEY: String = "1I9lI5tLamHXGMY1IDXiZaVS1IcGnpDV"

        // API params
        private const val API_PARAM_CLASSIFICATIONNAME: String = "classificationName"
        private const val API_PARAM_RADIUS: String = "radius"
        private const val API_PARAM_UNIT: String = "unit"
        private const val API_PARAM_ATTRACTIONID: String = "attractionId"
        private const val API_PARAM_PAGE: String = "page"
        private const val API_PARAM_KEYWORD: String = "keyword"

        // possible values for API param classificationName
        private const val API_CLASSIFICATIONNAME_VALUE_MUSIC: String = "music"

        private const val BASE_URL: String = "https://app.ticketmaster.com/$API_PACKAGE_DISCOVERY/$API_VERSION/{resource}.json?apikey=$API_KEY"

        // JSON Identifiers from API response
        private const val API_RESPONSE_EMBEDDED: String = "_embedded"
        private const val API_RESPONSE_EVENTS: String = "events"
        private const val API_RESPONSE_VENUES: String = "venues"
        private const val API_RESPONSE_ADDRESS: String = "address"
        private const val API_RESPONSE_LINE1: String = "line1"
        private const val API_RESPONSE_DATES: String = "dates"
        private const val API_RESPONSE_START: String = "start"
        private const val API_RESPONSE_DATETIME: String = "dateTime"
        private const val API_RESPONSE_IMAGES: String = "images"
        private const val API_RESPONSE_URL: String = "url"
        private const val API_RESPONSE_NAME: String = "name"
        private const val API_RESPONSE_ATTRACTIONS: String = "attractions"
        private const val API_RESPONSE_ID: String = "id"

        private var eventsPage: Int = 0

        private lateinit var queue: RequestQueue
        private var instance: TMApiWrapper? = null


        fun getInstance(context: Context): TMApiWrapper = instance ?: synchronized(this) {
            queue = Volley.newRequestQueue(context.applicationContext)
            instance = TMApiWrapper()
            return instance as TMApiWrapper
        }
    }

    private fun makeRequest(resource: String, params: HashMap<String, String>, onResponse: Response.Listener<JSONObject>) {
        val paramsStr = params.entries.stream()
            .map { e: Map.Entry<String, String> -> e.key + "=" + e.value }
            .toArray()
            .joinToString("&")
        val url = BASE_URL.replace("{resource}", resource) + "&" + paramsStr

        val request = JsonObjectRequest(Request.Method.GET, url, null, onResponse, { println("an error occured") })
        queue.add(request)
    }

    fun searchArtists(query: String, callback: (artists: ArrayList<Artist>) -> Unit) {
        val params = hashMapOf(API_PARAM_KEYWORD to query)
        makeRequest(API_RESOURCE_ATTRACTION, params) { response ->
            val artists = ArrayList<Artist>()
            val artistsJSON = response.getJSONObject(API_RESPONSE_EMBEDDED).getJSONArray(API_RESPONSE_ATTRACTIONS)

            for (i in 0 until artistsJSON.length()) {
                val artist = artistsJSON.getJSONObject(i)
                artists.add(Artist(artist.getString(API_RESPONSE_NAME), artist.getString(API_RESPONSE_ID)))
            }
            callback(artists)
        }
    }

    fun getEventsFromArtist(artist: Artist, callback: (events: ArrayList<Event>) -> Unit) {
        val params = hashMapOf(API_PARAM_ATTRACTIONID to artist.id)
        getEvents(params, callback)
    }

    fun getAllEvents(reset_page: Boolean = false, callback: (events: ArrayList<Event>) -> Unit) {
        if (reset_page) {
            eventsPage = 0
        }

        val params = getEventParams(eventsPage)

        getEvents(params) {events ->
            callback(events)
        }

        eventsPage += 1
    }

    fun getEventDetails(event: Event, callback: (events: ArrayList<Event>) -> Unit) {
        //TODO
    }

    private fun getEventParams(page: Int): HashMap<String, String> {
        return hashMapOf(
            API_PARAM_RADIUS to SettingsHandler.getInstance().radius.toString(),
            API_PARAM_UNIT to SettingsHandler.getInstance().unit,
            API_PARAM_CLASSIFICATIONNAME to API_CLASSIFICATIONNAME_VALUE_MUSIC,
            API_PARAM_PAGE to page.toString(),
        )
    }

    private fun getEvents(params: HashMap<String, String>, callback: (events: ArrayList<Event>) -> Unit) {
        val events = ArrayList<Event>()

        getEventsJson(params) { eventsJson ->
            for (i in 0 until eventsJson.length()) {
                val eventJson = eventsJson.getJSONObject(i)

                val location = eventJson
                    .getJSONObject(API_RESPONSE_EMBEDDED)
                    .getJSONArray(API_RESPONSE_VENUES)
                    .getJSONObject(0)
                    .getJSONObject(API_RESPONSE_ADDRESS)
                    .getString(API_RESPONSE_LINE1)

                val dateStr = eventJson
                    .getJSONObject(API_RESPONSE_DATES)
                    .getJSONObject(API_RESPONSE_START)
                    .getString(API_RESPONSE_DATETIME)

                val image = eventJson
                    .getJSONArray(API_RESPONSE_IMAGES)
                    .getJSONObject(1)
                    .getString(API_RESPONSE_URL)

                val artistName = eventJson
                    .getJSONObject(API_RESPONSE_EMBEDDED)
                    .getJSONArray(API_RESPONSE_ATTRACTIONS)
                    .getJSONObject(0)
                    .getString(API_RESPONSE_NAME)

                val name = eventJson.getString(API_RESPONSE_NAME)
                val date = LocalDateTime.parse(dateStr.substring(0, dateStr.length-1))

                val event = Event(
                    name,
                    artistName,
                    location,
                    date,
                    image,
                )
                events.add(event)
            }

            callback(events)
        }
    }

    private fun getEventsJson(params: HashMap<String, String>, callback: (eventsJson: JSONArray) -> Unit) {
        makeRequest(API_RESOURCE_EVENTS, params) { response ->
            val eventsJson = response.getJSONObject(API_RESPONSE_EMBEDDED).getJSONArray(API_RESPONSE_EVENTS)
            callback(eventsJson)
        }
    }
}