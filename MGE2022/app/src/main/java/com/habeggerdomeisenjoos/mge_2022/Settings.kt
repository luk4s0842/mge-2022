package com.habeggerdomeisenjoos.mge_2022

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class Settings private constructor() {
    companion object {
        private val UNIT_KM: String = "km"
        private val UNIT_MILES: String = "miles"
        private val DEFAULT_UNIT: String = UNIT_KM
        private val DEFAULT_RADIUS: Int = 100

        // Singleton
        private var instance: Settings? = null

        fun getInstance(): Settings = instance ?: synchronized(this) {
            instance = Settings()
            return instance as Settings
        }
    }

    // Backing fields
    private var _radius: Int = DEFAULT_RADIUS
    private var _unit: String = DEFAULT_UNIT
    private var _darkMode: Boolean = false

    // Getters/Setters
    var radius: Int
    get() = _radius
    set(value) {_radius = value}

    var unit: String //could be enum
    get() = _unit
    set(value) {_unit = value}

    var darkMode: Boolean
    get() = _darkMode
    set(value) {
        _darkMode = value
        setDarkModeMaterial(_darkMode)
    }

    fun getUnits(): List<String> {
        return listOf(
            UNIT_KM,
            UNIT_MILES,
        )
    }

    fun save() {
        //save to disk
    }

    private fun setDarkModeMaterial(value: Boolean) {
        if (value) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}