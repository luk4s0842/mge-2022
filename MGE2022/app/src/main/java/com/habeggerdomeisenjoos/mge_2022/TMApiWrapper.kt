package com.habeggerdomeisenjoos.mge_2022

public class TMApiWrapper { // static class or singleton
    companion object {
        private const val API_VERSION: String = "v2"

        //packages
        private const val API_PACKAGE_DISCOVERY: String = "discovery"

        // API params
        private const val API_PARAM_CLASSIFICATIONNAME: String = "classificationName"

        // possible values for API param classificationName
        private const val API_CLASSIFICATIONNAME_VALUE_MUSIC: String = "music"
        private const val API_CLASSIFICATIONNAME_VALUE_FILM: String = "film"
    }
    private val BASE_URL: String = "https://app.ticketmaster.com/$API_PACKAGE_DISCOVERY/$API_VERSION/{resource}.json?apikey=**$API_KEY"

    // ticket sellers
    enum class ticketSources {
        ticketmaster,
        universe,
        frontGate,
        ticketmaster_resale,
    }
    enum class classificationNames { //not sure if that works, example very bad
        music,
        film,
    }

    private fun createRequestUrl(
        sources: List<ticketSources>? = null,
        countryCode: String? = null,
        classificationName: classificationNames? = null){

    }
}