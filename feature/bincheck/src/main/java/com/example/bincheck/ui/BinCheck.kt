package com.example.bincheck.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.bincheck.viewmodel.BinCheckVM
import androidx.hilt.navigation.compose.hiltViewModel
@Composable
fun BinCheckScreen(viewModel: BinCheckVM = hiltViewModel()) {
    val uiState = viewModel.uiState.observeAsState()
}