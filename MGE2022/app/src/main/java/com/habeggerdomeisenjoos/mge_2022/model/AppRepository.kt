package com.habeggerdomeisenjoos.mge_2022.model

import android.content.Context
import androidx.room.Room
import com.habeggerdomeisenjoos.mge_2022.model.database.AppDatabase

class AppRepository {
    companion object {
        lateinit var database: AppDatabase

        fun initialize(context: Context) {
            database = Room.databaseBuilder(context, AppDatabase::class.java, "database.db")
                .allowMainThreadQueries()
                .build();
        }

        fun getArtists() : ArrayList<Artist> {
            return ArrayList(database.artistDao().getAllArtists())
        }

        fun getAllTmIds() : ArrayList<String> {
            return ArrayList(database.artistDao().getAllTmIds())
        }

        fun addArtist(tmId: String, name: String, description: String, picture_link: String) {
            val artist = Artist(tmId, name, description, picture_link)
            database.artistDao().insert(artist)
        }

        fun addArtist(artist: Artist) {
            database.artistDao().insert(artist)
        }

        fun deleteArtist(artist: Artist) {
            database.artistDao().delete(artist)
        }
    }
}