package com.example.bincheck.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.BinInfoRepository
import com.example.bininfo.BinlistResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinCheckVM @Inject constructor(
    val binInfoRepos: BinInfoRepository
): ViewModel() {
    private val _uiState = MutableLiveData(UIState())
    val uiState = _uiState
    fun getBinInfo(bin: String) {
        viewModelScope.launch {
            val networkResult = binInfoRepos.getBinInfo(bin)
            _uiState.value = UIState(
                binDetail = networkResult
            )
        }
    }
}

data class UIState(
    val showError: Boolean = false,
    val errorMessage: String = "",
    val binDetail: BinlistResponse? = null
)