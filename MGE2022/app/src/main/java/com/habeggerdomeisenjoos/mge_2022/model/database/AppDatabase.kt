package com.habeggerdomeisenjoos.mge_2022.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.habeggerdomeisenjoos.mge_2022.model.Artist


@Database (entities = [Artist::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistsDao
}