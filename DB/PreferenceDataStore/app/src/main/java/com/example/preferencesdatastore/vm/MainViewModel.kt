package com.example.preferencesdatastore.vm

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.preferencesdatastore.key.PreferencesKeys
import kotlinx.coroutines.launch


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class MainViewModel (
    application: Application
) : AndroidViewModel(application)  {

    private val context : Context get() = getApplication()
    val text = mutableStateOf("이곳에 값이 표시됩니다.")

    fun dataSave(){
        viewModelScope.launch {
            context.dataStore.edit {
                it[PreferencesKeys.MY_TEXT] = "데이터 값 저장"
            }
        }
    }

    fun dataLoad(){
        viewModelScope.launch {
            context.dataStore.data.collect {
                text.value = it[PreferencesKeys.MY_TEXT] ?: "저장된 데이터가 없습니다."
            }
        }
    }

}