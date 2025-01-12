package com.example.binlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashe.BinCache
import com.example.cashe.BinCacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinListVM @Inject constructor(
    val binCacheRepos: BinCacheRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<BinCache>?>(null)
    val uiState: StateFlow<List<BinCache>?> = _uiState

    init {
        viewModelScope.launch {
            binCacheRepos.getAllBinCaches().collect {
                _uiState.value = it
            }
        }
    }
}