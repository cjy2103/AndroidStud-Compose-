package com.example.wifiselect.ui.data

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class WifiInfoProvider {
    private val list = ArrayList<WifiInfo>()

    @SuppressLint("MutableCollectionMutableState")
    private val wifiList : MutableState<ArrayList<WifiInfo>> = mutableStateOf(list)



}