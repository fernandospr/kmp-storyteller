package com.github.fernandospr.storyteller.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import storyteller.composeapp.generated.resources.Res
import storyteller.composeapp.generated.resources.cat_name
import storyteller.composeapp.generated.resources.cow_name
import storyteller.composeapp.generated.resources.dog_name
import storyteller.composeapp.generated.resources.duck_name
import storyteller.composeapp.generated.resources.frog_name
import storyteller.composeapp.generated.resources.horse_name
import storyteller.composeapp.generated.resources.lion_name
import storyteller.composeapp.generated.resources.monkey_name
import storyteller.composeapp.generated.resources.pig_name
import storyteller.composeapp.generated.resources.rabbit_name

class CharacterRepository {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun getAllCharacters() = listOf(
        Character(name = stringResource(Res.string.dog_name), uiDescription = "🐶", Color(0xffD7CCC8)),
        Character(name = stringResource(Res.string.cat_name), uiDescription = "🐱", Color(0xffFFF9C4)),
        Character(name = stringResource(Res.string.monkey_name), uiDescription = "🐵", Color(0xffD7CCC8)),
        Character(name = stringResource(Res.string.lion_name), uiDescription = "🦁", Color(0xffFFE0B2)),
        Character(name = stringResource(Res.string.duck_name), uiDescription = "🦆", Color(0xffC8E6C9)),
        Character(name = stringResource(Res.string.rabbit_name), uiDescription = "🐰", Color(0xffF5F5F5)),
        Character(name = stringResource(Res.string.cow_name), uiDescription = "🐮", Color(0xffF5F5F5)),
        Character(name = stringResource(Res.string.pig_name), uiDescription = "🐷", Color(0xffF8BBD0)),
        Character(name = stringResource(Res.string.horse_name), uiDescription = "🐴", Color(0xffD7CCC8)),
        Character(name = stringResource(Res.string.frog_name), uiDescription = "🐸", Color(0xffDCEDC8)),
    )

    @Composable
    fun getCharacter(name: String) = getAllCharacters().first { it.name == name }
}