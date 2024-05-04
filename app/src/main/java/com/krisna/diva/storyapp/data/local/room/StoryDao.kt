package com.krisna.diva.storyapp.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krisna.diva.storyapp.data.model.StoryModel

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<StoryModel>)

    @Query("SELECT * FROM stories")
    fun getStories(): PagingSource<Int, StoryModel>

    @Query("DELETE FROM stories")
    suspend fun deleteAll()
}