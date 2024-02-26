package com.example.data

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class CharacterProvider {
    private val list = ArrayList<CharacterData>()

    @SuppressLint("MutableCollectionMutableState")
    private val characterList: State<ArrayList<CharacterData>> = mutableStateOf(list)

    init {

        addItem(com.example.moduledivide.R.string.baknana,
            com.example.moduledivide.R.string.bak_describe,
            com.example.moduledivide.R.drawable.baknana,
            com.example.moduledivide.R.string.baknana_link)
        addItem(com.example.moduledivide.R.string.djmax,
            com.example.moduledivide.R.string.djmax_describe,
            com.example.moduledivide.R.drawable.djmax_clear_fail,
            com.example.moduledivide.R.string.djmax_archive)
        addItem(com.example.moduledivide.R.string.djmax_falling_love,
            com.example.moduledivide.R.string.djmax_falling_love_describe,
            com.example.moduledivide.R.drawable.djmax_falling_in_love,
            com.example.moduledivide.R.string.djmax_falling_love_link)
        addItem(com.example.moduledivide.R.string.mwamwa,
            com.example.moduledivide.R.string.mwamwa_describe,
            com.example.moduledivide.R.drawable.mwama,
            com.example.moduledivide.R.string.mwamwa_link)
        addItem(com.example.moduledivide.R.string.tamtam,
            com.example.moduledivide.R.string.tamtam_describe,
            com.example.moduledivide.R.drawable.tamtam,
            com.example.moduledivide.R.string.tamtam_link)

    }

    private fun addItem(title: Int, describe: Int, image: Int, link: Int) {
        val character = CharacterData(title, describe, image, link)
        list.add(character)
    }

    fun getCharacterList(): State<ArrayList<CharacterData>> {
        return characterList
    }
}