package com.example.pennywise.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pennywise.components.NavBar
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
import com.example.pennywise.screens.BudgetScreen
import com.example.pennywise.screens.CategoriesDetailsScreen
import com.example.pennywise.screens.CategoriesScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PennyWiseNavigationComponent() {
    val navController = rememberNavController()
    val currentBackStack = navController.currentBackStackEntryAsState().value

    val focusManager = LocalFocusManager.current

    // Routes
    val ROUTE_BUDGET = "budget"
    val ROUTE_CATEGORIES = "categories"

    Scaffold(
//        bottomBar = {
//            TopAppBar(
//                title = {},
//                navigationIcon = {
//                    NavBar(navController, currentBackStack?.destination?.route)
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary
//                )
//            )
//        },
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp),
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 12.dp
            ) {
                NavBar(navController, currentBackStack?.destination?.route)
            }
        },
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = ROUTE_BUDGET) {
                composable(ROUTE_BUDGET) {
                    BudgetScreen(navController = navController)
                }
                composable(ROUTE_CATEGORIES) {
                    CategoriesScreen(navController = navController)
                }
                composable(
                    route = "details/{categoryId}",
                    arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getInt("categoryId")
                    CategoriesDetailsScreen(navController, categoryId)
                }
            }
        }
    }
}