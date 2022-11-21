package com.habeggerdomeisenjoos.mge_2022

import android.content.Context

class Settings private constructor() {
    companion object {
        private val UNIT_KM: String = "km"
        private val UNIT_MILES: String = "miles"
        private val DEFAULT_UNIT: String = UNIT_KM
        private val DEFAULT_RADIUS: Int = 100

        // Singleton
        private var INSTANCE: Settings? = null

        fun getInstance(): Settings = INSTANCE ?: synchronized(this) {
            return Settings()
        }
    }

    // Backing fields
    private var _radius: Int = DEFAULT_RADIUS
    private var _unit: String = DEFAULT_UNIT

    // Getters/Setters
    var radius: Int
    get() = _radius
    set(value) {_radius = value}

    var unit: String //could be enum
    get() = _unit
    set(value) {_unit = value}

    fun getUnits(): List<String> {
        return listOf(
            UNIT_KM,
            UNIT_MILES,
        )
    }

    fun save() {
        //save to disk
    }
}