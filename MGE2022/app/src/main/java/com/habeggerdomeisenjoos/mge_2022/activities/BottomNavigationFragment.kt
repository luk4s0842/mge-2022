package com.habeggerdomeisenjoos.mge_2022.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.habeggerdomeisenjoos.mge_2022.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BottomNavigationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomNavigationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        fun create() : BottomNavigationFragment {
            return BottomNavigationFragment()
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BottomNavigationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BottomNavigationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}