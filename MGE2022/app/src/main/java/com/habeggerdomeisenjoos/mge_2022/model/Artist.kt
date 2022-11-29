package com.habeggerdomeisenjoos.mge_2022.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Artist (
    @PrimaryKey
    @ColumnInfo(name = "tmId") val tmId: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "picture-URL") val pictureUrl: String?
) {
}