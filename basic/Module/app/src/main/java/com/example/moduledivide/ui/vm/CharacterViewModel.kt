package com.example.moduledivide.ui.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CharacterViewModel() : ViewModel() {

    constructor(character: com.example.data.CharacterData) : this(){
        _selectedCharacter.value = character
    }

    private val _selectedCharacter = mutableStateOf<com.example.data.CharacterData?>(null)

    fun setSelectedCharacter(character: com.example.data.CharacterData){
        _selectedCharacter.value = character
    }

    fun getSelectedCharacter() = _selectedCharacter

}