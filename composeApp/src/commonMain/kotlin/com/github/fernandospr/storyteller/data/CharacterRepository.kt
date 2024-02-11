package com.github.fernandospr.storyteller.data

import androidx.compose.runtime.Composable
import com.github.fernandospr.storyteller.MR
import dev.icerock.moko.resources.compose.stringResource

class CharacterRepository {

    @Composable
    fun getAllCharacters() = listOf(
        Character(name = stringResource(MR.strings.dog_name), uiDescription = "🐶"),
        Character(name = stringResource(MR.strings.cat_name), uiDescription = "🐱"),
        Character(name = stringResource(MR.strings.monkey_name), uiDescription = "🐵"),
        Character(name = stringResource(MR.strings.lion_name), uiDescription = "🦁"),
        Character(name = stringResource(MR.strings.duck_name), uiDescription = "🦆"),
        Character(name = stringResource(MR.strings.rabbit_name), uiDescription = "🐰"),
        Character(name = stringResource(MR.strings.cow_name), uiDescription = "🐮"),
        Character(name = stringResource(MR.strings.pig_name), uiDescription = "🐷"),
        Character(name = stringResource(MR.strings.horse_name), uiDescription = "🐴"),
        Character(name = stringResource(MR.strings.frog_name), uiDescription = "🐸"),
    )

    @Composable
    fun getCharacter(name: String) = getAllCharacters().first { it.name == name }
}