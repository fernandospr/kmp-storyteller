package com.github.fernandospr.storyteller.data

interface StoryTellerRepository {
    suspend fun getStory(promptPlaceholder: String, character: String): String
    fun clear()
}