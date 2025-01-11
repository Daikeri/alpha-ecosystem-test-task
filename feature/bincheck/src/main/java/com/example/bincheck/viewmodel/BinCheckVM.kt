package com.example.bincheck.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfo.BankInfo
import com.example.bininfo.BinInfoRepository
import com.example.bininfo.BinlistResponse
import com.example.bininfo.CountryInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import javax.inject.Inject

@HiltViewModel
class BinCheckVM @Inject constructor(
    val binInfoRepos: BinInfoRepository
): ViewModel() {
    private val _uiState = MutableLiveData(UIState(stringData = null, categoricalData = null))
    val uiState = _uiState
    fun getBinInfo(bin: String) {
        viewModelScope.launch {
            try {
                val networkResult = binInfoRepos.getBinInfo(bin)

                Log.e("From VM", "$networkResult")

                val cardTypeTransform = { value: String -> if (value =="debit") "Debit" else "Credit" }
                val isPrepaidTransform = { value: Boolean -> if (value) "Yes" else "No"}
                val cardTypeSetValue = setOf("Debit", "Credit")
                val isPrepaidSetValue = setOf("Yes", "No")

                val cardTypeCategory = when(networkResult.cardType) {
                    null -> null
                    else -> toCategoricalValue<String>(
                        currentVal = networkResult.cardType!!,
                        setOfValue = cardTypeSetValue,
                        transformRule = cardTypeTransform
                    )
                }

                val isPrepaidCategory = when(networkResult.isPrepaidCard) {
                    null -> null
                    else -> toCategoricalValue<Boolean>(
                        currentVal = networkResult.isPrepaidCard!!,
                        setOfValue = isPrepaidSetValue,
                        transformRule = isPrepaidTransform
                    )
                }

                val stringValue = listOf(
                    networkResult.paymentSystem,
                    networkResult.brand,
                    networkResult.country.name,
                    networkResult.country.latitude,
                    networkResult.country.longitude,
                    networkResult.bank.name,
                    networkResult.bank.url,
                    networkResult.bank.phone,
                    networkResult.bank.city
                )
                val stringValueHeader = listOf(
                    "Payment System",
                    "Brand",
                    "Country name",
                    "Latitude",
                    "Longitude",
                    "Bank name",
                    "Bank url",
                    "Bank City"
                )
                val categoricalValue = listOf(
                    cardTypeCategory,
                    isPrepaidCategory
                )
                val categoricalHeader = listOf(
                    "Card Type",
                    "Prepaid"
                )

                _uiState.value = UIState(
                    showContent = true,
                    stringData = stringValueHeader.zip(stringValue).toMap().filterValues { it != null },
                    categoricalData = categoricalHeader.zip(categoricalValue).toMap().filterValues { it != null }
                )
            } catch (e: Exception) {
                _uiState.value = UIState(
                    showError = true,
                    errorMessage = e.message.toString(),
                    stringData = null,
                    categoricalData = null
                )
            }
        }
    }

    fun clearUI() {
        _uiState.value = UIState(
            stringData = null,
            categoricalData = null
        )
    }

    private fun <T> toCategoricalValue(
        currentVal: T,
        setOfValue: Set<String>,
        transformRule: (T) -> String
    ): CategoricalValue {
        return CategoricalValue(
            targetValue = transformRule(currentVal),
            otherValue = setOfValue.filter { it != transformRule(currentVal) }
        )
    }
}


data class UIState(
    val showError: Boolean = false,
    val errorMessage: String = "",
    val showContent: Boolean = false,
    val stringData: Map<String,String?>?,
    val categoricalData: Map<String, CategoricalValue?>?
)

data class CategoricalValue(
    val targetValue: String,
    val otherValue: List<String>
)

/*
data class UIState(
    val showError: Boolean = false,
    val errorMessage: String = "",
    val paymentSystem: String? = null,
    val cardType: String? = null,
    val isPrepaidCard: Boolean? = null,
    val brand: String? = null,
    val countryName: String? = null,
    val countryLatitude: String? = null,
    val countryLongitude: String? = null,
    val bankName: String? = null,
    val bankUrl: String? = null,
    val bankPhone: String? = null,
    val bankCity: String? = null
)
*/

/*
 _uiState.value = UIState(
                    paymentSystem = networkResult.paymentSystem,
                    cardType = networkResult.cardType,
                    isPrepaidCard = networkResult.isPrepaidCard,
                    brand = networkResult.brand,
                    countryName = networkResult.country.name,
                    countryLatitude = networkResult.country.latitude,
                    countryLongitude = networkResult.country.longitude,
                    bankName = networkResult.bank.name,
                    bankUrl = networkResult.bank.url,
                    bankPhone = networkResult.bank.phone,
                    bankCity = networkResult.bank.url
                )
 */
