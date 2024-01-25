package com.example.staggeedgriddetail.data

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.staggeedgriddetail.R



class CharacterProvider {

    private val list = ArrayList<Character>()

    @SuppressLint("MutableCollectionMutableState")
    private val characterList: State<ArrayList<Character>> = mutableStateOf(list)



    init {
        addItem(R.string.baknana, R.string.bak_describe, R.drawable.baknana, R.string.baknana_link,200)
        addItem(R.string.djmax, R.string.djmax_describe, R.drawable.djmax_clear_fail, R.string.djmax_archive,160)
        addItem(
            R.string.djmax_falling_love,
            R.string.djmax_falling_love_describe,
            R.drawable.djmax_falling_in_love,
            R.string.djmax_falling_love_link
            ,150
        )
        addItem(R.string.mwamwa, R.string.mwamwa_describe, R.drawable.mwama,R.string.mwamwa_link,150)
        addItem(R.string.tamtam, R.string.tamtam_describe, R.drawable.tamtam,R.string.tamtam_link,180)

    }

    private fun addItem(title: Int, describe: Int, image: Int, link: Int, height : Int) {
        val character = Character(title, describe, image, link, height)
        list.add(character)
    }

    fun getCharacterList(): State<ArrayList<Character>> {
        return characterList
    }

}