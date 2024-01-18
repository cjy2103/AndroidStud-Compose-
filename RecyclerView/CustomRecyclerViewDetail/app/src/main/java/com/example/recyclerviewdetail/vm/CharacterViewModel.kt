package com.example.recyclerviewdetail.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recyclerviewdetail.data.Character

class CharacterViewModel() : ViewModel() {

    constructor(character: Character) : this(){
        _selectedCharacter.value = character
    }

    private val _selectedCharacter = mutableStateOf<Character?>(null)

    fun setSelectedCharacter(character: Character){
        _selectedCharacter.value = character
    }

    fun getSelectedCharacter() = _selectedCharacter

}