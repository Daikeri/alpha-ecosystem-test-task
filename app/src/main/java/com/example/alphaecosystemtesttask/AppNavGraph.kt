package com.example.alphaecosystemtesttask

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bincheck.ui.BinCheckScreen
import kotlinx.serialization.Serializable

@Serializable
object CheckBin

@Serializable
object RequestHistory

@Composable
fun ApplicationNavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CheckBin
    ) {
        composable<CheckBin> {
            BinCheckScreen()
        }

        composable<RequestHistory> {

        }
    }
}