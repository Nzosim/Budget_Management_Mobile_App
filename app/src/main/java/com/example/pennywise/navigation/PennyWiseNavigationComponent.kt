package com.example.pennywise.navigation

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
import com.example.pennywise.screens.BudgetScreen
import com.example.pennywise.screens.CategoriesScreen

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
        bottomBar = {
            TopAppBar(
                title = {
//                    Text(
//                        text = "PennyWise",
//                        color = MaterialTheme.colorScheme.onPrimary,
//                        style = MaterialTheme.typography.headlineMedium
//                    )
                },
                navigationIcon = {
                    if (currentBackStack?.destination?.route != ROUTE_BUDGET) {
                        IconButton(onClick = { navController.popBackStack() }) {
//                            Icon(
//                                imageVector = Icons.Default.ArrowBack,
//                                contentDescription = "Back",
//                                tint = MaterialTheme.colorScheme.onPrimary
//                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { navController.navigate("budget") }) {
                            Text(text = "Budget")
                        }

                        Button(onClick = { navController.navigate("categories") }) {
                            Text(text = "Categories")
                        }
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
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
                    // Appelle votre composable BudgetScreen. Adaptez l'appel si votre composable a une signature différente.
                    BudgetScreen(navController = navController)
                }

                composable(ROUTE_CATEGORIES) {
                    // Appelle votre composable CategoriesScreen. Adaptez l'appel si votre composable a une signature différente.
                    CategoriesScreen(navController = navController)
                }
            }
        }
    }
}