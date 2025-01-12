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

    private val _uiState = MutableStateFlow<UIState>(UIState())
    val uiState: StateFlow<UIState> = _uiState

    init {
        viewModelScope.launch {
            binCacheRepos.getAllBinCaches()
                .collect {cache ->
                    _uiState.value = UIState(
                        content = cache.map { fullBin ->
                            ShortBin(
                                id = fullBin.id,
                                bin = fullBin.bin,
                                time = fullBin.time,
                                bankName = fullBin.bankName,
                                paymentSystem = fullBin.paymentSystem,
                                countryName = fullBin.countryName
                            )
                        }
                    )
            }
        }
    }
}

data class UIState(
    val content: List<ShortBin>? = null
)
data class ShortBin(
    val id: Int,
    val bin: String,
    val time: String,
    val bankName: String?,
    val paymentSystem: String?,
    val countryName: String?
)