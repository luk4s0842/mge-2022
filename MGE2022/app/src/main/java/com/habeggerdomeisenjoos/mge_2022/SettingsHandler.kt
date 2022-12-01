package com.habeggerdomeisenjoos.mge_2022

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class SettingsHandler private constructor() {
    companion object {
        private const val UNIT_KM: String = "km"
        private const val UNIT_MILES: String = "miles"
        private const val DEFAULT_UNIT: String = UNIT_KM
        private const val DEFAULT_RADIUS: Int = 100
        private const val DEFAULT_DARK_MODE: Boolean = false

        // Singleton
        private var instance: SettingsHandler? = null

        fun getInstance(): SettingsHandler = instance ?: synchronized(this) {
            instance = SettingsHandler()
            return instance as SettingsHandler
        }
    }

    // Backing fields
    private var _radius: Int = DEFAULT_RADIUS
    private var _unit: String = DEFAULT_UNIT
    private var _darkMode: Boolean = DEFAULT_DARK_MODE

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
        setDarkModeMaterial(value)
    }

    fun getUnits(): List<String> {
        return listOf(
            UNIT_KM,
            UNIT_MILES,
        )
    }

    fun save(context: Context) {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.settings_file_key), Context.MODE_PRIVATE)

        with(sharedPreferences.edit()) {
            putString(context.getString(R.string.unit_setting_id), unit)
            putInt(context.getString(R.string.radius_setting_id), radius)
            putBoolean(context.getString(R.string.dark_mode_setting_id), darkMode)
            apply()
        }
    }

    fun load(context: Context) {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.settings_file_key), Context.MODE_PRIVATE)

        unit = sharedPreferences.getString(context.getString(R.string.unit_setting_id), DEFAULT_UNIT).toString()
        radius = sharedPreferences.getInt(context.getString(R.string.radius_setting_id), DEFAULT_RADIUS)
        darkMode = sharedPreferences.getBoolean(context.getString(R.string.dark_mode_setting_id), DEFAULT_DARK_MODE)
    }

    private fun setDarkModeMaterial(value: Boolean) {
        if (value) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}