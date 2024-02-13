package com.github.fernandospr.storyteller.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.github.fernandospr.storyteller.MR
import dev.icerock.moko.resources.compose.stringResource

class CharacterRepository {

    @Composable
    fun getAllCharacters() = listOf(
        Character(name = stringResource(MR.strings.dog_name), uiDescription = "🐶", Color(0xffD7CCC8)),
        Character(name = stringResource(MR.strings.cat_name), uiDescription = "🐱", Color(0xffFFF9C4)),
        Character(name = stringResource(MR.strings.monkey_name), uiDescription = "🐵", Color(0xffD7CCC8)),
        Character(name = stringResource(MR.strings.lion_name), uiDescription = "🦁", Color(0xffFFE0B2)),
        Character(name = stringResource(MR.strings.duck_name), uiDescription = "🦆", Color(0xffC8E6C9)),
        Character(name = stringResource(MR.strings.rabbit_name), uiDescription = "🐰", Color(0xffF5F5F5)),
        Character(name = stringResource(MR.strings.cow_name), uiDescription = "🐮", Color(0xffF5F5F5)),
        Character(name = stringResource(MR.strings.pig_name), uiDescription = "🐷", Color(0xffF8BBD0)),
        Character(name = stringResource(MR.strings.horse_name), uiDescription = "🐴", Color(0xffD7CCC8)),
        Character(name = stringResource(MR.strings.frog_name), uiDescription = "🐸", Color(0xffDCEDC8)),
    )

    @Composable
    fun getCharacter(name: String) = getAllCharacters().first { it.name == name }
}