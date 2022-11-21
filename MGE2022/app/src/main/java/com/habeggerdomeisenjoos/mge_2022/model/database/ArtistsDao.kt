package com.habeggerdomeisenjoos.mge_2022.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.habeggerdomeisenjoos.mge_2022.model.Artist

@Dao
interface ArtistsDao {
    @Query("SELECT * FROM artist")
    fun getAllArtists(): List<Artist>

    @Query("SELECT tmId FROM artist")
    fun getAllTmIds(): List<Int>

    @Insert
    fun insertAll(vararg artists: Artist)

    @Insert
    fun insert(artists: Artist)

    @Delete
    fun delete(artist: Artist)
}