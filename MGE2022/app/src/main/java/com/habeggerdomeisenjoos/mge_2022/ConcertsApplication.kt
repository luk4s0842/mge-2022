package com.habeggerdomeisenjoos.mge_2022

import android.app.Application
import android.content.Context
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository

class ConcertsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppRepository.initialize(this)
        SettingsHandler.getInstance().load(this)
    }
}