package com.example.eduskunta.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eduskunta.components.MpDetailScreen
import com.example.eduskunta.components.MpListScreen
import com.example.eduskunta.local.AppDatabase
import com.example.eduskunta.network.RetrofitInstance
import com.example.eduskunta.repository.MpRepository
import com.example.eduskunta.repository.ReviewRepository
import com.example.eduskunta.viewModel.MpDetailViewModel
import com.example.eduskunta.viewModel.MpDetailViewModelFactory
import com.example.eduskunta.viewModel.MpListViewModel
import com.example.eduskunta.viewModel.MpListViewModelFactory

@Composable
fun AppNavigation(
    database: AppDatabase
) {
    val navController = rememberNavController()

    val mpRepository = MpRepository(
        mpDao = database.mpDao(),
        apiService = RetrofitInstance.api
    )

    val  reviewRepository = ReviewRepository(
        reviewDao = database.reviewDao()
    )

    NavHost(
        navController = navController,
        startDestination = Screen.MpList.route
    ) {
        composable(Screen.MpList.route) {
            val viewModel: MpListViewModel = viewModel(
                factory = MpListViewModelFactory(mpRepository)
            )
            MpListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { personNumber ->
                    navController.navigate(Screen.MpDetail.createRoute(personNumber))
                }
            )
        }

        composable(
            route = Screen.MpDetail.route,
            arguments = listOf(navArgument("personNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val personNumber = backStackEntry.arguments?.getInt("personNumber") ?: return@composable
            val viewModel: MpDetailViewModel = viewModel(
                factory = MpDetailViewModelFactory(mpRepository, reviewRepository, personNumber)
            )
            MpDetailScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}