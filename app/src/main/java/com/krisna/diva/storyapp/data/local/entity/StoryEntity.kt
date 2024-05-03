package com.krisna.diva.storyapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,
)