package com.habeggerdomeisenjoos.mge_2022

import android.app.Application
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository
import com.habeggerdomeisenjoos.mge_2022.services.SettingsHandler
import com.habeggerdomeisenjoos.mge_2022.services.TMApiWrapper

class ConcertsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppRepository.initialize(this)
        SettingsHandler.getInstance().load(this)
        TMApiWrapper.getInstance().load(this)
    }
}
