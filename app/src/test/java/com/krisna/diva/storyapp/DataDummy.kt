package com.krisna.diva.storyapp

import com.krisna.diva.storyapp.data.model.StoryModel

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryModel> {
        val items: MutableList<StoryModel> = arrayListOf()
        for (i in 0..100) {
            val story = StoryModel(
                i.toString(),
                "photo $i",
                "name $i",
                "description $i",
                i.toDouble(),
                i.toDouble()
            )
            items.add(story)
        }
        return items
    }
}