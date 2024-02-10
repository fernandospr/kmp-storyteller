package com.github.fernandospr.storyteller.data

interface StoryTellerRepository {
    suspend fun getStory(character: String): String
}