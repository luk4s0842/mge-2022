package com.habeggerdomeisenjoos.mge_2022.activities

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.services.TMApiWrapper
import com.habeggerdomeisenjoos.mge_2022.adapter.EventsAdapter
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository
import com.habeggerdomeisenjoos.mge_2022.model.Event
import com.habeggerdomeisenjoos.mge_2022.utils.PermissionsUtils


class ConcertsActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var noEventsFoundLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_events)
        noEventsFoundLabel = findViewById(R.id.no_events_found_label)

        recyclerView = findViewById(R.id.events_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (PermissionsUtils.hasLocationPermission(this)) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            loadEvents()
        } else {
            requestLocationPermission()
        }
    }

    private fun getSortedEvents(events: ArrayList<Event>) : ArrayList<Event> {
        return ArrayList(events.sortedBy { it.datetime })
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) ||
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)-> {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    loadEvents()
                } else -> {
                    loadEventsByLocation(null)
                }
            }
        }
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ))
    }

    private fun loadEvents() {
        try {
            if (PermissionsUtils.hasLocationPermission(this)) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        loadEventsByLocation(location)
                    }
            } else {
                loadEventsByLocation(null)
            }
        } catch (e: SecurityException) {
            loadEventsByLocation(null)
        }
    }

    private fun loadEventsByLocation(location: Location?) {
        var currentEvents = ArrayList<Event>()
        var artists = AppRepository.getArtists()
        var latlong = getLatLong(location)

        noEventsFoundLabel.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE

        for (artist in artists) {
            TMApiWrapper.getInstance().getEventsFromArtist(latlong, artist) { events: ArrayList<Event> ->
                if (events.size > 0) {
                    noEventsFoundLabel.visibility = View.INVISIBLE
                    recyclerView.visibility = View.VISIBLE
                }

                currentEvents.addAll(events)
                val adapter = EventsAdapter(getSortedEvents(currentEvents))
                recyclerView.adapter = adapter
            }
        }
    }

    private fun getLatLong(location: Location?) : String? {
        if (location == null) return null
        return "${location.latitude},${location.longitude}"
    }
}

