package com.habeggerdomeisenjoos.mge_2022

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.habeggerdomeisenjoos.mge_2022.model.Artist
import com.habeggerdomeisenjoos.mge_2022.model.Event
import org.json.JSONArray
import org.json.JSONException
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
        private const val API_PARAM_SEGMENT: String = "segmentId"
        private const val API_PARAM_SEGMENT_VALUE: String = "KZFzniwnSyZfZ7v7nJ" // this segment id is for: Music

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
        private const val API_RESPONSE_CLASSIFICATIONS: String = "classifications"
        private const val API_RESPONSE_TOTAL_ELEMENTS: String = "totalElements"

        private lateinit var queue: RequestQueue
        private var instance: TMApiWrapper? = null


        fun getInstance(): TMApiWrapper = instance ?: synchronized(this) {
            instance = TMApiWrapper()
            return instance as TMApiWrapper
        }
    }

    fun load(context: Context) {
        queue = Volley.newRequestQueue(context.applicationContext)
        instance = TMApiWrapper()
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
        val params = hashMapOf(API_PARAM_KEYWORD to query, API_PARAM_SEGMENT to API_PARAM_SEGMENT_VALUE)
        makeRequest(API_RESOURCE_ATTRACTION, params) { response ->
            if (!hasElements(response)) {
                callback(ArrayList())
                return@makeRequest
            }

            val artists = ArrayList<Artist>()
            val artistsJSON = response.getJSONObject(API_RESPONSE_EMBEDDED).getJSONArray(API_RESPONSE_ATTRACTIONS)

            for (i in 0 until artistsJSON.length()) {
                try {
                    val artist = artistsJSON.getJSONObject(i)

                    val artistImage = artist
                        .getJSONArray(API_RESPONSE_IMAGES)
                        .getJSONObject(0)
                        .getString(API_RESPONSE_URL)

                    val artistGenre = artist
                        .getJSONArray(API_RESPONSE_CLASSIFICATIONS)
                        .getJSONObject(0)
                        .getJSONObject("genre")
                        .getString(API_RESPONSE_NAME)

                    artists.add(Artist(
                        artist.getString(API_RESPONSE_ID),
                        artist.getString(API_RESPONSE_NAME),
                        "Genre: $artistGenre",
                        artistImage
                    ))
                } catch (e: JSONException) {
                    // ignore artist if JSONException occurs
                }
            }
            callback(artists)
        }
    }

    fun getEventsFromArtist(latlong: String?, artist: Artist, callback: (events: ArrayList<Event>) -> Unit) {
        if (artist.tmId != null) {
            val params = hashMapOf(API_PARAM_ATTRACTIONID to artist.tmId)
            if (latlong != null) {
                params["latlong"] = latlong
            }
            getEvents(params, callback)
        }
    }

    private fun getEventParams(): HashMap<String, String> {
        return hashMapOf(
            API_PARAM_RADIUS to SettingsHandler.getInstance().radius.toString(),
            API_PARAM_UNIT to SettingsHandler.getInstance().unit,
            API_PARAM_CLASSIFICATIONNAME to API_CLASSIFICATIONNAME_VALUE_MUSIC,
        )
    }

    private fun getEvents(params: HashMap<String, String>, callback: (events: ArrayList<Event>) -> Unit) {
        val events = ArrayList<Event>()

        getEventsJson(params) { eventsJson ->
            for (i in 0 until eventsJson.length()) {
                try {
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
                    val date = LocalDateTime.parse(dateStr.substring(0, dateStr.length - 1))

                    val event = Event(
                        name,
                        artistName,
                        location,
                        date,
                        image,
                    )
                    events.add(event)
                } catch (e: JSONException) {
                    // ignore event if JSONException occurs
                }
            }

            callback(events)
        }
    }

    private fun getEventsJson(params: HashMap<String, String>, callback: (eventsJson: JSONArray) -> Unit) {
        params.putAll(getEventParams())
        makeRequest(API_RESOURCE_EVENTS, params) { response ->
            if (hasElements(response)) {
                val eventsJson = response.getJSONObject(API_RESPONSE_EMBEDDED).getJSONArray(API_RESPONSE_EVENTS)
                callback(eventsJson)
            }
        }
    }

    private fun hasElements(apiResponse: JSONObject) : Boolean {
        val elemCount = apiResponse.getJSONObject(API_PARAM_PAGE).getInt(API_RESPONSE_TOTAL_ELEMENTS)
        return elemCount > 0
    }
}
