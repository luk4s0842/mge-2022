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

            if (getArtists().isEmpty()) {
                addArtist(1223, "Peter Fox", "Er ist ein talentierter Sänger", "https://images.berliner-kurier.de/2022/10/21/3ac5c065-818e-4928-bbb1-6b88e665494d.jpeg?rect=0%2C3%2C2048%2C1365&w=1024&auto=format")
            }
        }

        fun getArtists() : ArrayList<Artist> {
            return ArrayList(database.artistDao().getAllArtists())
        }

        fun getAllTmIds() : ArrayList<Int> {
            return ArrayList(database.artistDao().getAllTmIds())
        }

        fun addArtist(tmId: Int, name: String, description: String, picture_link: String) {
            val artist = Artist(tmId, name, description, picture_link)
            database.artistDao().insert(artist)
        }

        fun deleteArtist(artist: Artist) {
            database.artistDao().delete(artist)
        }
    }
}