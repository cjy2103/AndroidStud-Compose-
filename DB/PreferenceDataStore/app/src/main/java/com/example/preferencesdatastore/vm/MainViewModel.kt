package com.example.preferencesdatastore.vm

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")
@HiltViewModel
class MainViewModel @Inject constructor(
    private val application : Application
) : ViewModel()  {

    private val context : Context get() = application.applicationContext
    val text = mutableStateOf("이곳에 값이 표시됩니다.")

    fun dataSave(){
        viewModelScope.launch {
            context.dataStore.edit {
                
            }
        }
    }

}