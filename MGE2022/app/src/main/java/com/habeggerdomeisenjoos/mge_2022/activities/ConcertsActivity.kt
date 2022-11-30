package com.habeggerdomeisenjoos.mge_2022.activities

import android.Manifest
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.TMApiWrapper
import com.habeggerdomeisenjoos.mge_2022.adapter.EventsAdapter
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository
import com.habeggerdomeisenjoos.mge_2022.model.Event
import com.habeggerdomeisenjoos.mge_2022.utils.PermissionsUtils


class ConcertsActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!PermissionsUtils.hasLocationPermission(this)) {
            requestLocationPermission()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContentView(R.layout.activity_events)
        
        var recyclerView = findViewById<RecyclerView>(R.id.events_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadEvents(recyclerView)
    }

    private fun getSortedEvents(events: ArrayList<Event>) : ArrayList<Event> {
        return ArrayList(events.sortedBy { it.datetime })
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // location access granted.
                } else -> {
                    // No location access granted.
                }
            }
        }
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun loadEvents(recyclerView: RecyclerView) {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    loadEventsByLocation(recyclerView, location)
                }
        } catch (e: SecurityException) {
            loadEventsByLocation(recyclerView, null)
        }
    }

    private fun loadEventsByLocation(recyclerView: RecyclerView, location: Location?) {
        var currentEvents = ArrayList<Event>()
        var artists = AppRepository.getArtists()
        for (artist in artists) {
            TMApiWrapper.getInstance().getEventsFromArtist(getLatLong(location), artist) { events: ArrayList<Event> ->
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

