package com.example.myapplication.ui.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.ApiResult
import com.example.myapplication.data.model.TestResponse
import com.example.myapplication.data.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: TestRepository
) : ViewModel() {

    private val _testList = MutableStateFlow<ApiResult<List<TestResponse>>?>(null)  // ✅ 수정 완료
    val testList: StateFlow<ApiResult<List<TestResponse>>?> = _testList.asStateFlow()

    init {
        fetchTestData()
    }

    fun fetchTestData() {
        viewModelScope.launch {
            _testList.value = repository.fetchTestList()
        }
    }
}