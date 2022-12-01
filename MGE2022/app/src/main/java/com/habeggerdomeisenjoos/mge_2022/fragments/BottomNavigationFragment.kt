package com.habeggerdomeisenjoos.mge_2022.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.activities.ArtistsActivity
import com.habeggerdomeisenjoos.mge_2022.activities.ConcertsActivity
import com.habeggerdomeisenjoos.mge_2022.activities.SettingsActivity

/**
 * A simple [Fragment] subclass.
 * Use the [BottomNavigationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomNavigationFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        var activityNavId = hashMapOf(
            "activities.ConcertsActivity" to R.id.navbar_events,
            "activities.ArtistsActivity" to R.id.navbar_artists,
            "activities.SettingsActivity" to R.id.navbar_settings
        )
        var activityName = activity?.localClassName
        bottomNav.selectedItemId = activityNavId[activityName]!!

        bottomNav?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navbar_artists -> {
                    var intent = Intent(activity, ArtistsActivity::class.java)
                    startActivity(intent)
                }
                R.id.navbar_events -> {
                    var intent = Intent(activity, ConcertsActivity::class.java)
                    startActivity(intent)
                }
                R.id.navbar_settings -> {
                    var intent = Intent(activity, SettingsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            }
            activity?.overridePendingTransition(0, 0);
            true
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BottomNavigationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BottomNavigationFragment().apply {}
    }
}